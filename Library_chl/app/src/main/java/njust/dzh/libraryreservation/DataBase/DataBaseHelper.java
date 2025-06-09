package njust.dzh.libraryreservation.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

// 数据库访问类
public class DataBaseHelper extends SQLiteOpenHelper {
    // 数据库名称
    public static final String DATABASE = "library.db";

    // 数据库版本号
    public static final int VERSION = 1;

    // 创建用户表User
    public static final String CREATE_USER = "create table User ("
            + "account text primary key,"
            + "password text)";
    // 创建一楼已预定座位表
    public static final String CREATE_FIRST = "create table First ("
            + "id integer primary key,"
            + "account text)";
    // 创建二楼已预定座位表
    public static final String CREATE_SECOND = "create table Second ("
            + "id integer primary key,"
            + "account text)";
    // 创建三楼已预定座位表
    public static final String CREATE_THIRD = "create table Third ("
            + "id integer primary key,"
            + "account text)";
    // 创建学生表Student
    public static final String CREATE_STUDENT = "create table Student ("
            + "account text primary key,"
            + "name text,"
            + "age text,"
            + "phone text,"
            + "college text)";

    // 创建DB对象时的构造函数
    public DataBaseHelper(Context context) {
        super(context, DATABASE, null, VERSION);
    }

    // 创建数据库
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_USER);
        db.execSQL(CREATE_FIRST);
        db.execSQL(CREATE_SECOND);
        db.execSQL(CREATE_THIRD);
        db.execSQL(CREATE_STUDENT);
    }
    // 升级数据库
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists User");
        db.execSQL("drop table if exists First");
        db.execSQL("drop table if exists Second");
        db.execSQL("drop table if exists Third");
        db.execSQL("drop table if exists Student");
        onCreate(db);
    }
}
