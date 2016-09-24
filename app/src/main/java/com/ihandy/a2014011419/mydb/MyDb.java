package com.ihandy.a2014011419.mydb;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

/**
 * Created by byr on 2016/9/4.
 */
public class MyDb extends SQLiteOpenHelper {

    private static final String[] DB_TABLE_ALL = {"sports", "top_stories", "technology", "health", "world", "national", "entertainment"};

    private static final int DB_VERSION = 2;
    private static final String DB_NAME = "myNews.db";
    private static final String DB_TABLE ="favourite";   //创建表名为news的表


    private static final String DB_COLUMN_TITLE ="_title";
    private static final String DB_COLUMN_URL ="_url";
    private static final String DB_COLUMN_NEWSID ="_newsId";
    private static final String DB_COLUMN_IMAGINURL ="_imgUrl";
    private static final String DB_COLUMN_TIME = "_time";
    private static final String DB_COLUMN_ORIGIN = "_origin";

    private static final String[] DB_COLUMNS_ARRAY = new String[] {DB_COLUMN_NEWSID, DB_COLUMN_TITLE, DB_COLUMN_IMAGINURL, DB_COLUMN_URL, DB_COLUMN_ORIGIN, DB_COLUMN_TIME};
    public static String getDbTime() {
        return DB_COLUMN_TIME;
    }

    public static String getDbOrigin() {
        return DB_COLUMN_ORIGIN;
    }

    public static String getDbTable() {
        return DB_TABLE;
    }

    public static String getDbColumnTitle() {
        return DB_COLUMN_TITLE;
    }

    public static String getDbColumnUrl() {
        return DB_COLUMN_URL;
    }

    public static String getDbColumnImaginurl() {
        return DB_COLUMN_IMAGINURL;
    }

    public static String getDbColumnNewsid() {
        return DB_COLUMN_NEWSID;
    }

    public static String[] getDbColumnArray() {
        return DB_COLUMNS_ARRAY;
    }

    public static String getDbTableAll(int i) {
        return DB_TABLE_ALL[i];
    }

    public MyDb(Context context){
        super(context, DB_NAME, null, DB_VERSION);
        Log.e("MyDb", "constructor");
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        StringBuffer sb = new StringBuffer();
        sb.append("create table if not exists ");
        sb.append(DB_TABLE +"(");
        sb.append(DB_COLUMN_NEWSID +" varchar(100) primary key ,");   //设置主键
        sb.append(DB_COLUMN_TITLE + " varchar(100) ,");
        sb.append(DB_COLUMN_URL +" varchar(100) ,");
        sb.append(DB_COLUMN_IMAGINURL +" varchar(100) ,");
        sb.append(DB_COLUMN_TIME +" varchar(100) ,");
        sb.append(DB_COLUMN_ORIGIN +" varchar(100)");
        sb.append(")");
        Log.e("MyDb oncreate", sb.toString());
        db.execSQL(sb.toString());

        for (int i = 0; i < DB_TABLE_ALL.length; i++){
            sb = new StringBuffer();
            sb.append("create table if not exists ");
            sb.append(DB_TABLE_ALL[i] +"(");
            sb.append(DB_COLUMN_NEWSID +" varchar(100) primary key ,");   //设置主键
            sb.append(DB_COLUMN_TITLE + " varchar(100) ,");
            sb.append(DB_COLUMN_URL +" varchar(100) ,");
            sb.append(DB_COLUMN_IMAGINURL +" varchar(100) ,");
            sb.append(DB_COLUMN_TIME +" varchar(100) ,");
            sb.append(DB_COLUMN_ORIGIN +" varchar(100)");
            sb.append(")");
            Log.e("MyDb oncreate " + i, sb.toString());
            db.execSQL(sb.toString());
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("onUpgrad", "drop");
        String sqlCmd = "drop table if exists " + DB_TABLE;
        Log.e("onUpgrad", sqlCmd);
        db.execSQL(sqlCmd);


        for (int i = 0; i < DB_TABLE_ALL.length; i++){
            sqlCmd = "drop table if exists " + DB_TABLE_ALL[i];
            Log.e("onUpgrad " + i, sqlCmd);
            db.execSQL(sqlCmd);
        }

        //创建
        onCreate(db);
    }
}
