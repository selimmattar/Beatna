package com.mporject.interns.beatna;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{
    public static final String DATABASE_NAME="User.db";
    public static final String TABLE_FAVORITES="favorites_table";
    public static final String COL_ID="ID";
    public static final String COL_CATEGORY="CATEGORY";
    public static final String COL_MOOD="MOOD";



    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);

    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+ TABLE_FAVORITES + " (ID INTEGER PRIMARY KEY AUTOINCREMENT, CATEGORY TEXT, MOOD TEXT) ");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_FAVORITES);
        onCreate(db);
    }

    public boolean insertData(String mood, String category)
    {
        SQLiteDatabase db= this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_CATEGORY,category);
        contentValues.put(COL_MOOD,mood);
        long result=db.insert(TABLE_FAVORITES,null,contentValues);
        if(result==-1)
            return false;
        else
            return true;

    }

    public Cursor getAllData()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_FAVORITES,null);
        return res;
    }

    public Cursor getBestMood()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select MOOD, count(MOOD) as occ from "+TABLE_FAVORITES+" GROUP BY MOOD ORDER BY occ DESC ",null);
        return res;
    }

    public Cursor getBest3Moods()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select MOOD, count(MOOD) as occ from "+TABLE_FAVORITES+" GROUP BY MOOD ORDER BY occ DESC  limit 3 ",null);
        return res;
    }

    public Cursor getBest3Categories()
    {
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select CATEGORY, count(CATEGORY) as occ from "+TABLE_FAVORITES+" GROUP BY CATEGORY ORDER BY occ DESC  limit 3 ",null);
        return res;

    }
}
