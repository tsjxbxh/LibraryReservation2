package njust.dzh.libraryreservation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import njust.dzh.libraryreservation.Bean.Aphorism;
import njust.dzh.libraryreservation.R;
import njust.dzh.libraryreservation.Util.HttpUtil;
import njust.dzh.libraryreservation.Util.URLUtils;
import okhttp3.Call;
import okhttp3.Response;

public class StepActivity extends AppCompatActivity implements View.OnClickListener, SensorEventListener{
    private SensorManager sManager;
    private Sensor mSensorAccelerometer;
    private TextView tv_step;
    private Button btn_start;
    private int step = 0;         //步数
    private double oriValue = 0;  //初始值
    private double lstValue = 0;  //上次的值
    private double curValue = 0;  //当前值
    private boolean motiveState = true;     //是否处于运动状态
    private boolean processState = false;   //标记当前是否在计步

    private TextView tvSaying;
    private TextView tvTransl;
    private TextView tvSource;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_step);
        bindViews();
    }
    private void bindViews() {
        sManager = (SensorManager) getSystemService(SENSOR_SERVICE);
        mSensorAccelerometer = sManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        sManager.registerListener(this, mSensorAccelerometer, SensorManager.SENSOR_DELAY_UI);

        tv_step = findViewById(R.id.tv_step);
        btn_start = findViewById(R.id.btn_start);
        btn_start.setOnClickListener(this);
        tvSaying = findViewById(R.id.tv_saying);
        tvTransl = findViewById(R.id.tv_transl);
        tvSource = findViewById(R.id.tv_source);
        // 模拟器向服务器发送Http的Get请求
        HttpUtil.sendOkHttpRequest(URLUtils.index_url, new okhttp3.Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                // 如果api次数不足或者模拟器无网络，则直接返回
                return;
            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                // 如果api次数还有，得到服务器返回的具体内容
                String result = response.body().string();
                // 解析json对象的数据
                parseShowData(result);
            }
        });
    }

    // 解析并展示数据
    private void parseShowData(String result) {
        // 使用gson解析数据
        Aphorism aphorism = new Gson().fromJson(result, Aphorism.class);
        Aphorism.NewslistDTO newslistDTO = aphorism.getNewslist().get(0);
        String saying = newslistDTO.getSaying();
        String transl = newslistDTO.getTransl();
        String source = newslistDTO.getSource();
        // 从子线程转到主线程，进行UI操作
        runOnUiThread(new Runnable() {
            @Override
            public void run() {
                // 显示名言
                tvSaying.setText(saying);
                // 显示翻译
                tvTransl.setText(transl);
                // 显示来源
                tvSource.setText(source);
            }
        });

    }

    @Override
    public void onSensorChanged(SensorEvent sensorEvent) {
        double range = 1; // 设定一个精度范围
        float[] values = sensorEvent.values;
        curValue = magnitude(values[0], values[1], values[2]); // 计算当前的模
        // 向上加速的状态
        if (motiveState == true) {
            if (curValue >= lstValue)  {
                lstValue = curValue;
            } else {
                // 检测到一次峰值
                if (Math.abs(curValue - lstValue) > range) {
                    oriValue = curValue;
                    motiveState = false;
                }
            }
        }
        // 向下加速的状态
        if (motiveState == false) {
            if (curValue <= lstValue) {
                lstValue = curValue;
            } else {
                if (Math.abs(curValue - lstValue) > range) {
                    // 检测到一次峰值
                    oriValue = curValue;
                    if (processState == true) {
                        // 步数更新
                        tv_step.setText(++step + "");
                    }
                }
                motiveState = true;
            }
        }
    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int i) {

    }

    @Override
    public void onClick(View view) {
        step = 0;
        tv_step.setText("0");
        if (processState == true) {
            btn_start.setText("开始计步");
            processState = false;
        } else {
            btn_start.setText("停止计步");
            processState = true;
        }
    }

    //向量求模
    public double magnitude(float x, float y, float z) {
        double magnitude = 0;
        magnitude = Math.sqrt(x * x + y * y + z * z);
        return magnitude;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        sManager.unregisterListener(this);
    }
}