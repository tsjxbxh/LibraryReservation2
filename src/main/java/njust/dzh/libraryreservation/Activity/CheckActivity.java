package njust.dzh.libraryreservation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;

import java.io.IOException;

import njust.dzh.libraryreservation.App.LoginActivity;
import njust.dzh.libraryreservation.Bean.Aphorism;
import njust.dzh.libraryreservation.Bean.Seat;
import njust.dzh.libraryreservation.DataBase.SeatDao;
import njust.dzh.libraryreservation.Util.HttpUtil;
import njust.dzh.libraryreservation.R;
import njust.dzh.libraryreservation.Util.URLUtils;
import okhttp3.Call;
import okhttp3.Response;

public class CheckActivity extends AppCompatActivity {
    private SeatDao seatDao;
    private TextView tvFloor, tvSeat;
    private Button btnCancel;
    private String account;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_check);
        initView();
    }

    private void initView() {
        // 获取控件实例
        tvFloor = findViewById(R.id.tv_floor);
        tvSeat = findViewById(R.id.tv_seat);
        btnCancel = findViewById(R.id.btn_cancel);
        // 取出传递过来的account
        account = getIntent().getStringExtra(LoginActivity.ACCOUNT);
        // 进行数据库操作
        seatDao = new SeatDao(this);
        seatDao.open();
        Seat seat = seatDao.getSeats(account);
        // 如果有预定的座位
        if (seat != null) {
            int floor = seatDao.getFloor(seat);
            tvFloor.setText(floor + "楼");
            int id = seat.getId();
            tvSeat.setText(id + "");
        }
        seatDao.close();
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (seat == null) {
                    Toast.makeText(CheckActivity.this, "您还没有预订任何座位。", Toast.LENGTH_SHORT).show();
                } else {
                    seatDao.open();
                    seatDao.cancelFloor(seat);
                    seatDao.close();
                    Toast.makeText(CheckActivity.this, "退订成功！", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

}