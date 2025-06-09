package njust.dzh.libraryreservation.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;

import java.util.ArrayList;
import java.util.List;

import njust.dzh.libraryreservation.Bean.Seat;

public class SeatDao {
    private Context context;
    private DataBaseHelper dataBaseHelper;
    private SQLiteDatabase sqLiteDatabase;

    public SeatDao(Context context) {
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

    // 获取一楼已预定的座位信息
    public List<Seat> getFirstSeats() {
        Cursor cursor = sqLiteDatabase.query("First", null, null, null, null, null, null);
        List<Seat> seatList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String account = cursor.getString(cursor.getColumnIndex("account"));
                Seat seat = new Seat(id, account);
                seatList.add(seat);
            } while (cursor.moveToNext());
        }
        return seatList;
    }

    // 获取二楼已预定的座位信息
    public List<Seat> getSecondSeats() {
        Cursor cursor = sqLiteDatabase.query("Second", null, null, null, null, null, null);
        List<Seat> seatList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String account = cursor.getString(cursor.getColumnIndex("account"));
                Seat seat = new Seat(id, account);
                seatList.add(seat);
            } while (cursor.moveToNext());
        }
        return seatList;
    }

    // 获取三楼已预定的座位信息
    public List<Seat> getThirdSeats() {
        Cursor cursor = sqLiteDatabase.query("Third", null, null, null, null, null, null);
        List<Seat> seatList = new ArrayList<>();
        if (cursor.moveToFirst()) {
            do {
                int id = cursor.getInt(cursor.getColumnIndex("id"));
                String account = cursor.getString(cursor.getColumnIndex("account"));
                Seat seat = new Seat(id, account);
                seatList.add(seat);
            } while (cursor.moveToNext());
        }
        return seatList;
    }

    // 预定一楼座位
    public void addFirstSeats(Seat seat) {
        ContentValues values = new ContentValues();
        values.put("id", seat.getId());
        values.put("account", seat.getAccount());
        sqLiteDatabase.insert("First", null, values);
    }

    // 预定二楼座位
    public void addSecondSeats(Seat seat) {
        ContentValues values = new ContentValues();
        values.put("id", seat.getId());
        values.put("account", seat.getAccount());
        sqLiteDatabase.insert("Second", null, values);
    }

    // 预定三楼座位
    public void addThirdSeats(Seat seat) {
        ContentValues values = new ContentValues();
        values.put("id", seat.getId());
        values.put("account", seat.getAccount());
        sqLiteDatabase.insert("Third", null, values);
    }

    // 得到楼层
    public int getFloor(Seat seat) {
        String table[] = new String[]{"First", "Second", "Third"};
        for (int i = 0; i < table.length; i++) {
            Cursor cursor = sqLiteDatabase.query(table[i], null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String account = cursor.getString(cursor.getColumnIndex("account"));
                    if (seat.getAccount().equals(account)) {
                        return i +1;
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return -1;
    }

    // 退订
    public void cancelFloor(Seat seat) {
        String table[] = new String[]{"First", "Second", "Third"};
        int index = getFloor(seat);
        sqLiteDatabase.delete(table[index - 1], "account = ?", new String[] {seat.getAccount()});
    }

    // 获取用户预定的座位信息
    public Seat getSeats(String name) {
        // 三张表的字符串数组
        String table[] = new String[]{"First", "Second", "Third"};
        for (int i = 0; i < table.length; i++) {
            Cursor cursor = sqLiteDatabase.query(table[i], null, null, null, null, null, null);
            if (cursor.moveToFirst()) {
                do {
                    String account = cursor.getString(cursor.getColumnIndex("account"));
                    if (name.equals(account)) {
                        int id = cursor.getInt(cursor.getColumnIndex("id"));
                        Seat seat = new Seat(id, account);
                        return seat;
                    }
                } while (cursor.moveToNext());
            }
            cursor.close();
        }
        return null;
    }
}
