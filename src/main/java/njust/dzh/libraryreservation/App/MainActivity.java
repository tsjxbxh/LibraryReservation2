package njust.dzh.libraryreservation.App;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import njust.dzh.libraryreservation.Activity.CheckActivity;
import njust.dzh.libraryreservation.Activity.FirstFloorActivity;
import njust.dzh.libraryreservation.Activity.StepActivity;
import njust.dzh.libraryreservation.Activity.StudentActivity;
import njust.dzh.libraryreservation.Activity.SecondFloorActivity;
import njust.dzh.libraryreservation.Activity.ThirdFloorActivity;
import njust.dzh.libraryreservation.PagerAdapter;
import njust.dzh.libraryreservation.R;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Button reservation, check, student, step, exit;
    private RadioButton firstFloor, secondFloor, thirdFloor;
    private String account;
    private ViewPager libraryVp;
    private LinearLayout pointLayout;
    private PagerAdapter pagerAdapter;
    // 声明图片数组
    private int[] imgIds = {R.drawable.page1, R.drawable.page2, R.drawable.page3, R.drawable.page4, R.drawable.page5};
    // 声明VIerPager的数据源
    List<ImageView> ivList;
    // 声明管理小圆点的集合
    List<ImageView> pointList;

    // 完成定时装置，实现自动滑动的效果
    Handler handler = new Handler() {
        @Override
        public void handleMessage(@NonNull Message msg) {
            if (msg.what == 1) {
                // 获取当前ViewPager显示的页面
                int currenItem = libraryVp.getCurrentItem();
                // 判断是否为最后一张，如果是最后一张则回第一张，否则显示下一张
                if (currenItem == ivList.size() - 1) {
                    libraryVp.setCurrentItem(0);
                } else {
                    libraryVp.setCurrentItem(++currenItem);
                }
                // 形成循环发送接收消息的效果，在接收消息时也发送消息
                handler.sendEmptyMessageDelayed(1, 2000);
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        // 获取控件实例，设置监听器
        reservation = findViewById(R.id.reservation);
        check = findViewById(R.id.check);
        student = findViewById(R.id.student);
        step = findViewById(R.id.step);
        pointLayout = findViewById(R.id.point_layout);
        libraryVp = findViewById(R.id.library_vp);
        exit = findViewById(R.id.exit);

        reservation.setOnClickListener(this);
        check.setOnClickListener(this);
        student.setOnClickListener(this);
        step.setOnClickListener(this);
        exit.setOnClickListener(this);

        // 获取单选按钮实例，设置监听器
        firstFloor = findViewById(R.id.first_floor);
        secondFloor = findViewById(R.id.second_floor);
        thirdFloor = findViewById(R.id.third_floor);

        // 获取传递数据
        account = getIntent().getStringExtra(LoginActivity.ACCOUNT);
        initPager();
        setVPListener();
        // 延迟2s发送消息，切换viewpager的图片
        handler.sendEmptyMessageDelayed(1, 2000);
    }

    // 设置ViewPager监听器
    private void setVPListener() {
        libraryVp.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                for (int i = 0; i < pointList.size(); i++) {
                    pointList.get(i).setImageResource(R.drawable.point_normal);
                }
                pointList.get(position).setImageResource(R.drawable.point_focus);
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }
    private void initPager() {
        ivList = new ArrayList<>();
        pointList = new ArrayList<>();
        for (int i = 0; i < imgIds.length; i++) {
            ImageView iv = new ImageView(this);
            iv.setImageResource(imgIds[i]);
            iv.setScaleType(ImageView.ScaleType.FIT_XY);
            // 设置图片view的宽高
            LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT,
                    LinearLayout.LayoutParams.MATCH_PARENT);
            iv.setLayoutParams(lp);
            // 将图片view加载到集合中
            ivList.add(iv);
            // 创建图片对应的指示器小圆点
            ImageView piv = new ImageView(this);
            piv.setImageResource(R.drawable.point_normal);
            LinearLayout.LayoutParams plp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            plp.setMargins(20, 0, 0, 0);
            piv.setLayoutParams(plp);
            // 将小圆点添加到布局当中
            pointLayout.addView(piv);
            // 为了方便操作，将小圆点加入统一管理的集合
            pointList.add(piv);
        }
        pointList.get(0).setImageResource(R.drawable.point_focus);
        pagerAdapter = new PagerAdapter(this, ivList);
        libraryVp.setAdapter(pagerAdapter);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.reservation:
                if (firstFloor.isChecked()) {
                    Intent intent = new Intent(MainActivity.this, FirstFloorActivity.class);
                    intent.putExtra(LoginActivity.ACCOUNT, account);
                    Toast.makeText(this, "进入一楼座位表", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else if (secondFloor.isChecked()){
                    Intent intent = new Intent(MainActivity.this, SecondFloorActivity.class);
                    intent.putExtra(LoginActivity.ACCOUNT, account);
                    Toast.makeText(this, "进入二楼座位表", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                } else if (thirdFloor.isChecked()){
                    Intent intent = new Intent(MainActivity.this, ThirdFloorActivity.class);
                    intent.putExtra(LoginActivity.ACCOUNT, account);
                    Toast.makeText(this, "进入三楼座位表", Toast.LENGTH_SHORT).show();
                    startActivity(intent);
                }
                break;

            case R.id.check:
                Intent intent = new Intent(MainActivity.this, CheckActivity.class);
                intent.putExtra(LoginActivity.ACCOUNT, account);
                startActivity(intent);
                break;

            case R.id.student:
                Intent intent2 = new Intent(MainActivity.this, StudentActivity.class);
                intent2.putExtra(LoginActivity.ACCOUNT, account);
                startActivity(intent2);
                break;

            case R.id.step:
                Intent intent3 = new Intent(MainActivity.this, StepActivity.class);
                intent3.putExtra(LoginActivity.ACCOUNT, account);
                startActivity(intent3);
                break;

            case R.id.exit:
                Intent intent4 = new Intent(MainActivity.this, LoginActivity.class);
                startActivity(intent4);
                finish();
                break;

            default:
                break;
        }
    }
}