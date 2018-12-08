package com.nopupio.www.otpsenderupdated;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.support.annotation.Nullable;

/**
 * Created by ASUS on 4/1/2018.
 */

public class DBIP extends SQLiteOpenHelper {


    public DBIP(Context context) {
        super(context, DATA_BASE, null, VERSION);
    }


    public void saveIP(String ipaddress) {
        deleteAll();
        ContentValues contentValues = new ContentValues();
        contentValues.put(Column.IP, ipaddress);
        SQLiteDatabase db = this.getWritableDatabase();
        db.insert(TABLE, null, contentValues);
    }

    @Nullable
    public String getIP() {
        String sqlQuery = "SELECT * FROM " + TABLE;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(sqlQuery, null);
        if (cursor.moveToFirst()) {
            String ip = cursor.getString(cursor.getColumnIndex(Column.IP));
            return ip;
        }
        return null;
    }

    public int deleteAll() throws SQLException {
        SQLiteDatabase db = this.getWritableDatabase();
        int result = db.delete(TABLE, null, null);
        return result;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String sqlQuery = "CREATE TABLE" + " "
                + TABLE + " ("
                + Column.IP + " " + "TEXT" + " "
                + " ) ";
        try {
            db.execSQL(sqlQuery);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "
                + TABLE);
        onCreate(db);
    }


    private static final int VERSION = 1;
    private static final String DATA_BASE = "ip_database";
    private static final String TABLE = "ip_table";

    private static final class Column {
        private static final String IP = "ip";
    }


}