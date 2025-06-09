package njust.dzh.libraryreservation.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import njust.dzh.libraryreservation.Bean.Seat;
import njust.dzh.libraryreservation.Bean.Student;


public class StudentDao {
    private Context context;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public StudentDao(Context context) {
        this.context = context;
    }

    // 打开数据库
    public void open() throws SQLiteException {
        dataBaseHelper = new DataBaseHelper(context);
        try {
            sqLiteDatabase = dataBaseHelper.getWritableDatabase();
        } catch (SQLiteException e) {
            sqLiteDatabase = dataBaseHelper.getReadableDatabase();
        }
    }

    // 关闭数据库
    public void close() {
        if (sqLiteDatabase != null) {
            sqLiteDatabase.close();
            sqLiteDatabase = null;
        }
    }

    // 根据学号来获得学生信息
    public Student getInformation(String sid) {
        Cursor cursor = sqLiteDatabase.query("Student", null, null, null, null, null, null);
        if (cursor.moveToFirst()) {
            do {
                String account = cursor.getString(cursor.getColumnIndex("account"));
                if (account.equals(sid)) {
                    String name = cursor.getString(cursor.getColumnIndex("name"));
                    String age = cursor.getString(cursor.getColumnIndex("age"));
                    String phone = cursor.getString(cursor.getColumnIndex("phone"));
                    String college = cursor.getString(cursor.getColumnIndex("college"));
                    Student student = new Student(account, name, age, phone, college);
                    cursor.close();
                    return student;
                }
            } while (cursor.moveToNext());
        }
        cursor.close();
        return null;
    }

    // 修改学生信息
    public void updateInformation(Student student) {
        // 将学生信息存储到values键值对中
        ContentValues values = new ContentValues();
        values.put("name", student.getName());
        values.put("age", student.getAge());
        values.put("phone", student.getPhone());
        values.put("college", student.getCollege());
        // 如果该学生信息不存在，则先插入
        if (getInformation(student.getAccount()) == null) {
            values.put("account", student.getAccount());
            sqLiteDatabase.insert("Student", null, values);
        } else { // 如果该学生信息存在，则更新
            sqLiteDatabase.update("Student", values, "account = ?", new String[]{student.getAccount()});
        }
    }
}
