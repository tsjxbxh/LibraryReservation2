package njust.dzh.libraryreservation.Activity;

import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.List;

import njust.dzh.libraryreservation.App.LoginActivity;
import njust.dzh.libraryreservation.Bean.Seat;
import njust.dzh.libraryreservation.DataBase.SeatDao;
import njust.dzh.libraryreservation.R;

public class ThirdFloorActivity extends AppCompatActivity {
    private FloatingActionButton fab;
    private SeatDao seatDao;
    private List<Seat> seatList;
    private Seat seat;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_third_floor);
        initView();
    }

    // 初始化
    private void initView() {
        initSeats();
        initRadioGroups();
        fab = findViewById(R.id.fab);
        account = getIntent().getStringExtra(LoginActivity.ACCOUNT);

        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog alertDialog = new AlertDialog.Builder(view.getContext())
                        .setTitle("提示")
                        .setIcon(R.drawable.ic_order)
                        .setMessage("您确定要预订该位置吗？")
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                seatDao.open();
                                if (seatDao.getSeats(account) != null) {
                                    Toast.makeText(ThirdFloorActivity.this, "预订失败，您有预订的座位未退订", Toast.LENGTH_SHORT).show();
                                } else {
                                    seatDao.addThirdSeats(seat);
                                    Toast.makeText(ThirdFloorActivity.this, "预订成功！", Toast.LENGTH_SHORT).show();
                                }
                                seatDao.close();
                            }
                        })
                        .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Toast.makeText(ThirdFloorActivity.this, "预订已取消", Toast.LENGTH_SHORT).show();
                            }
                        })
                        .show();
            }
        });
    }

    // 初始化所有单选按钮
    private void initSeats() {
        seatDao = new SeatDao(this);
        seatDao.open();
        seatList = seatDao.getThirdSeats();
        seatDao.close();
        // 获取所有单选按钮实例
        RadioButton seat11 = findViewById(R.id.seat11);
        RadioButton seat12 = findViewById(R.id.seat12);
        RadioButton seat21 = findViewById(R.id.seat21);
        RadioButton seat22 = findViewById(R.id.seat22);
        RadioButton seat31 = findViewById(R.id.seat31);
        RadioButton seat32 = findViewById(R.id.seat32);
        RadioButton seat41 = findViewById(R.id.seat41);
        RadioButton seat42 = findViewById(R.id.seat42);
        RadioButton seat43 = findViewById(R.id.seat43);
        RadioButton seat51 = findViewById(R.id.seat51);
        RadioButton seat52 = findViewById(R.id.seat52);
        RadioButton seat61 = findViewById(R.id.seat61);
        RadioButton seat62 = findViewById(R.id.seat62);
        RadioButton seat63 = findViewById(R.id.seat63);
        RadioButton seat71 = findViewById(R.id.seat71);
        RadioButton seat72 = findViewById(R.id.seat72);
        RadioButton seat81 = findViewById(R.id.seat81);
        RadioButton seat82 = findViewById(R.id.seat82);
        RadioButton seat83 = findViewById(R.id.seat83);

        RadioButton []radioArray = new RadioButton[] {seat11, seat12, seat21, seat22, seat31,
                seat32, seat41, seat42, seat51, seat52, seat61, seat62, seat63, seat71, seat72,
                seat81, seat82, seat83};

        // 遍历所有单选按钮
        for (int i = 0; i < radioArray.length; i++) {
            int id = radioArray[i].getId();
            boolean ordered = false;
            // 遍历已选中的单选列表
            for (int j = 0; j < seatList.size(); j++) {
                if (seatList.get(j).getId() == id) {
                    radioArray[i].setBackgroundResource(R.drawable.bg_seats_ordered);
                    radioArray[i].setEnabled(false);
                    ordered = true;
                    break;
                }
            }
            // 退订座位后恢复可选
            if (!ordered && !radioArray[i].isEnabled()) {
                    radioArray[i].setEnabled(true);
                    radioArray[i].setBackgroundResource(R.drawable.bg_seats);
            }
        }
    }

    // 初始化所有单选按钮组
    private void initRadioGroups() {
        RadioGroup radioGroup1 = findViewById(R.id.radio_group1);
        RadioGroup radioGroup2 = findViewById(R.id.radio_group2);
        RadioGroup radioGroup3 = findViewById(R.id.radio_group3);
        RadioGroup radioGroup4 = findViewById(R.id.radio_group4);
        RadioGroup radioGroup5 = findViewById(R.id.radio_group5);
        RadioGroup radioGroup6 = findViewById(R.id.radio_group6);
        RadioGroup radioGroup7 = findViewById(R.id.radio_group7);
        RadioGroup radioGroup8 = findViewById(R.id.radio_group8);
        // 单选按钮组的集合
        RadioGroup [] radioGroups = new RadioGroup[] {radioGroup1, radioGroup2, radioGroup3, radioGroup4,
        radioGroup5, radioGroup6, radioGroup7, radioGroup8};
        // 每一组的监听器
        radioGroup1.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // 清除其他组的选择
                for (int k = 0; k < radioGroups.length; k++) {
                    if (k == 0) continue;
                    if (radioGroups[k].getCheckedRadioButtonId() != -1) {
                        radioGroups[k].clearCheck();
                    }
                }
                // 获取seat对象
                seat = new Seat(i, account);
            }
        });
        radioGroup2.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // 清除其他行的选择
                for (int k = 0; k < radioGroups.length; k++) {
                    if (k == 1) continue;
                    if (radioGroups[k].getCheckedRadioButtonId() != -1) {
                        radioGroups[k].clearCheck();
                    }
                }
                seat = new Seat(i, account);
            }
        });
        radioGroup3.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // 清除其他行的选择
                for (int k = 0; k < radioGroups.length; k++) {
                    if (k == 2) continue;
                    if (radioGroups[k].getCheckedRadioButtonId() != -1) {
                        radioGroups[k].clearCheck();
                    }
                }
                seat = new Seat(i, account);
            }
        });
        radioGroup4.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // 清除其他行的选择
                for (int k = 0; k < radioGroups.length; k++) {
                    if (k == 3) continue;
                    if (radioGroups[k].getCheckedRadioButtonId() != -1) {
                        radioGroups[k].clearCheck();
                    }
                }
                seat = new Seat(i, account);
            }
        });
        radioGroup5.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // 清除其他行的选择
                for (int k = 0; k < radioGroups.length; k++) {
                    if (k == 4) continue;
                    if (radioGroups[k].getCheckedRadioButtonId() != -1) {
                        radioGroups[k].clearCheck();
                    }
                }
                seat = new Seat(i, account);
            }
        });
        radioGroup6.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // 清除其他行的选择
                for (int k = 0; k < radioGroups.length; k++) {
                    if (k == 5) continue;
                    if (radioGroups[k].getCheckedRadioButtonId() != -1) {
                        radioGroups[k].clearCheck();
                    }
                }
                seat = new Seat(i, account);
            }
        });
        radioGroup7.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // 清除其他行的选择
                for (int k = 0; k < radioGroups.length; k++) {
                    if (k == 6) continue;
                    if (radioGroups[k].getCheckedRadioButtonId() != -1) {
                        radioGroups[k].clearCheck();
                    }
                }
                seat = new Seat(i, account);
            }
        });
        radioGroup8.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                // 清除其他行的选择
                for (int k = 0; k < radioGroups.length; k++) {
                    if (k == 7) continue;
                    if (radioGroups[k].getCheckedRadioButtonId() != -1) {
                        radioGroups[k].clearCheck();
                    }
                }
                seat = new Seat(i, account);
            }
        });

    }

}