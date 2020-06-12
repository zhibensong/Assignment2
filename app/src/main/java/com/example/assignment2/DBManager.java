package com.example.assignment2;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DBManager {

    private DBHelper dbHelper;

    public DBManager(Context context){
        dbHelper = new DBHelper(context);
    }

    public void insert(int io, int image, String type, double number, int year, int month, int day){
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues content = new ContentValues();
        content.put("IO", io);
        content.put("image", image);
        content.put("type", type);
        content.put("number", number);
        content.put("year", year);
        content.put("month", month);
        content.put("day", day);
        db.insert("accounts", null, content);
        db.close();
    }

    public List<entry> queryByMonth(int year, int month){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<entry> entries = new ArrayList<>();
        String querySql = "Select IO, image, type, number from accounts where year = ? and month = ?";
        Cursor cursor = db.rawQuery(querySql, new String[]{String.valueOf(year), String.valueOf(month)});
        if(cursor.moveToFirst()){
            do {
                entry entry = new entry();
                entry.setIO(cursor.getInt(cursor.getColumnIndex("IO")));
                entry.setImage(cursor.getInt(cursor.getColumnIndex("image")));
                entry.setTypeName(cursor.getString(cursor.getColumnIndex("type")));
                entry.setNumber(cursor.getDouble(cursor.getColumnIndex("number")));
                entries.add(entry);
            }while(cursor.moveToNext());
        }
        return entries;
    }

    public List<entry> queryByDay(int year, int month, int day){
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<entry> entries = new ArrayList<>();
        String querySql = "Select IO, type, number from accounts where year = ? and month = ? and day = ?";
        Cursor cursor = db.rawQuery(querySql, new String[]{String.valueOf(year), String.valueOf(month), String.valueOf(day)});
        if(cursor.moveToFirst()){
            do {
                entry entry = new entry();
                entry.setImage(cursor.getInt(cursor.getColumnIndex("IO")));
                entry.setTypeName(cursor.getString(cursor.getColumnIndex("type")));
                entry.setNumber(cursor.getDouble(cursor.getColumnIndex("number")));
                entries.add(entry);
            }while(cursor.moveToNext());
        }
        return entries;
    }
}
