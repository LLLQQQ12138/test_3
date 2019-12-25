package com.example.musicplayer.musicplayer.DataBase;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBGedan extends SQLiteOpenHelper {
    private String sql = "create table music(" +
            "songname text not null," +  //歌名
            "singer text," +       //歌
            "length int," +
            "path text not null)";    //歌的路径

    public DBGedan(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }
    public DBGedan(Context context) {
        super(context, "music_db", null, 1);
    }
    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(sql);
    }
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}