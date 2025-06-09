package njust.dzh.libraryreservation.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import njust.dzh.libraryreservation.App.LoginActivity;
import njust.dzh.libraryreservation.Bean.Student;
import njust.dzh.libraryreservation.DataBase.StudentDao;
import njust.dzh.libraryreservation.R;

public class StudentActivity extends AppCompatActivity implements View.OnClickListener {
    // 控件的声明
    private EditText etAccount;
    private EditText etName;
    private EditText etAge;
    private EditText etPhone;
    private EditText etCollege;
    private Button btnUpdate;
    private Button btnSave;

    private StudentDao studentDao;
    private String account;
    private Student student;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);
        initView();
    }

    // 初始化
    private void initView() {
        etAccount = findViewById(R.id.et_account);
        etName = findViewById(R.id.et_name);
        etAge = findViewById(R.id.et_age);
        etPhone = findViewById(R.id.et_phone);
        etCollege = findViewById(R.id.et_college);
        btnUpdate = findViewById(R.id.btn_update);
        btnSave = findViewById(R.id.btn_save);
        // 学号不可编辑
        account = getIntent().getStringExtra(LoginActivity.ACCOUNT);
        etAccount.setText(account);
        // 初始其他控件也不可编辑
        etAccount.setEnabled(false);
        etName.setEnabled(false);
        etAge.setEnabled(false);
        etPhone.setEnabled(false);
        etCollege.setEnabled(false);
        // 如果学生信息不为空则显示
        studentDao = new StudentDao(this);
        studentDao.open();
        Student student = studentDao.getInformation(account);
        if (student != null) {
            etName.setText(student.getName());
            etAge.setText(student.getAge());
            etPhone.setText(student.getPhone());
            etCollege.setText(student.getCollege());
        }
        studentDao.close();
        // 点击修改按钮进入修改模式
        btnUpdate.setOnClickListener(this);
        // 点击保存按钮进入查看模式
        btnSave.setOnClickListener(this);
    }
    // 查看模式，禁止编辑
    private void disableEditor() {
        etName.setEnabled(false);
        etAge.setEnabled(false);
        etPhone.setEnabled(false);
        etCollege.setEnabled(false);
        studentDao.open();
        // 获取控件中的文本内容
        String name = etName.getText().toString().trim();
        String age = etAge.getText().toString().trim();
        String phone = etPhone.getText().toString().trim();
        String college = etCollege.getText().toString().trim();
        student = new Student(account, name, age, phone, college);
        // 将数据保存到表中
        studentDao.updateInformation(student);
        studentDao.close();
        // 按钮显示的切换
        btnSave.setVisibility(View.INVISIBLE);
        btnUpdate.setVisibility(View.VISIBLE);
    }

    // 编辑模式，可以编辑
    private void enableEditor() {
        etName.setEnabled(true);
        etAge.setEnabled(true);
        etPhone.setEnabled(true);
        etCollege.setEnabled(true);
        btnSave.setVisibility(View.VISIBLE);
        btnUpdate.setVisibility(View.INVISIBLE);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_update:
                enableEditor();
                break;
            case R.id.btn_save:
                disableEditor();
                break;
            default:
                break;
        }
    }
}