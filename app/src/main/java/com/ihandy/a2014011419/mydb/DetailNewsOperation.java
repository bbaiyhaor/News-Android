package com.ihandy.a2014011419.mydb;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.ihandy.a2014011419.news.NewsItem;

import java.util.ArrayList;

/**
 * Created by byr on 2016/9/4.
 */
public class DetailNewsOperation {
    private MyDb helper;
    public DetailNewsOperation(Context context){
        Log.e("DetailNewsOperation", "创建db");
        helper = new MyDb(context);   //与数据库建立连接
        if (context == null)
            Log.e("???", "!!!");
        helper.getWritableDatabase();
    }
    //插入数据
    public void insertDetailNews(NewsItem news){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sqlCmd = "insert into " + MyDb.getDbTable()
                + "("
                + MyDb.getDbColumnNewsid() + ","
                + MyDb.getDbColumnTitle() + ","
                + MyDb.getDbOrigin() + ","
                + MyDb.getDbTime() + ","
                + MyDb.getDbColumnImaginurl() + ","
                + MyDb.getDbColumnUrl() + ")"
                + "values("
                + "\"" + news.getIndex() + "\","
                + "\"" + news.getTitle() + "\","
                + "\"" + news.getSource() + "\","
                + "\"" + news.getPublishDate() + "\","
                + "\"" + news.getImageUrl() + "\","
                + "\"" + news.getContentUrl() + "\")";
        Log.e("insert db", sqlCmd);
        db.execSQL(sqlCmd);
        db.close();
    }

    public void insertDetailNewsAllT(NewsItem news, int index){
        SQLiteDatabase db = helper.getWritableDatabase();
        String sqlCmd = "insert into " + MyDb.getDbTableAll(index)
                + "("
                + MyDb.getDbColumnNewsid() + ","
                + MyDb.getDbColumnTitle() + ","
                + MyDb.getDbOrigin() + ","
                + MyDb.getDbTime() + ","
                + MyDb.getDbColumnImaginurl() + ","
                + MyDb.getDbColumnUrl() + ")"
                + "values("
                + "\"" + news.getIndex() + "\","
                + "\"" + news.getTitle() + "\","
                + "\"" + news.getSource() + "\","
                + "\"" + news.getPublishDate() + "\","
                + "\"" + news.getImageUrl() + "\","
                + "\"" + news.getContentUrl() + "\")";
        Log.e("insert db all", sqlCmd);
        db.execSQL(sqlCmd);
        db.close();
    }

    //删除数据
    public void del(long news_id){   //根据传入参数newsid删除数据
        SQLiteDatabase db = helper.getReadableDatabase();
        String sqlCmd = "delete from " + MyDb.getDbTable() + " where " + MyDb.getDbColumnNewsid() + " = \"" + news_id + "\"";
        Log.e("del db", sqlCmd);
        db.execSQL(sqlCmd);
        db.close();
    }

    public void delAllT(long news_id, int index){   //根据传入参数newsid删除数据
        SQLiteDatabase db = helper.getReadableDatabase();
        String sqlCmd = "delete from " + MyDb.getDbTableAll(index) + " where " + MyDb.getDbColumnNewsid() + " = \"" + news_id + "\"";
        Log.e("del db all", sqlCmd);
        db.execSQL(sqlCmd);
        db.close();
    }


    //  输出所有新闻
    public ArrayList<NewsItem> findAll(){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(MyDb.getDbTable(),
                null, null, null, null, null, null);
        Log.e("find all db", String.valueOf(cursor.getCount()));
        ArrayList<NewsItem> newsArr = new ArrayList<NewsItem>(cursor.getCount());
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                //NewsItem(long index, String imageUrl, String title, String publishDate, String source, int readTimes, String preview, String contentUrl)
                NewsItem order = new NewsItem(cursor.getLong(cursor.getColumnIndex(MyDb.getDbColumnNewsid())),
                        cursor.getString(cursor.getColumnIndex(MyDb.getDbColumnImaginurl())),
                        cursor.getString(cursor.getColumnIndex(MyDb.getDbColumnTitle())),
                        cursor.getString(cursor.getColumnIndex(MyDb.getDbTime())),
                        cursor.getString(cursor.getColumnIndex(MyDb.getDbOrigin())), 0,
                        "",
                        cursor.getString(cursor.getColumnIndex(MyDb.getDbColumnUrl())));
                newsArr.add(order);
            }
        }
        cursor.close();
        db.close();
        return newsArr;
    }


    public ArrayList<NewsItem> findAllAllT(int index){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(MyDb.getDbTableAll(index),
                null, null, null, null, null, null);
        Log.e("find all db all", String.valueOf(cursor.getCount()));
        ArrayList<NewsItem> newsArr = new ArrayList<NewsItem>(cursor.getCount());
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                //NewsItem(long index, String imageUrl, String title, String publishDate, String source, int readTimes, String preview, String contentUrl)
                NewsItem order = new NewsItem(cursor.getLong(cursor.getColumnIndex(MyDb.getDbColumnNewsid())),
                        cursor.getString(cursor.getColumnIndex(MyDb.getDbColumnImaginurl())),
                        cursor.getString(cursor.getColumnIndex(MyDb.getDbColumnTitle())),
                        cursor.getString(cursor.getColumnIndex(MyDb.getDbTime())),
                        cursor.getString(cursor.getColumnIndex(MyDb.getDbOrigin())), 0,
                        "",
                        cursor.getString(cursor.getColumnIndex(MyDb.getDbColumnUrl())));
                newsArr.add(order);
            }
        }
        cursor.close();
        db.close();
        return newsArr;
    }


    //查询数据
    public ArrayList<NewsItem> findSelected(long news_id){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(MyDb.getDbTable(),
                MyDb.getDbColumnArray(),
                MyDb.getDbColumnNewsid() + " = ?",
                new String[] {String.valueOf(news_id)},
                null, null, null);
        Log.e("find db", String.valueOf(cursor.getCount()));
        ArrayList<NewsItem> newsArr = new ArrayList<NewsItem>(cursor.getCount());
            if (cursor.getCount() > 0) {
                while (cursor.moveToNext()) {
                    //NewsItem(long index, String imageUrl, String title, String publishDate, String source, int readTimes, String preview, String contentUrl)
                    NewsItem order = new NewsItem(cursor.getLong(cursor.getColumnIndex(MyDb.getDbColumnNewsid())),
                            cursor.getString(cursor.getColumnIndex(MyDb.getDbColumnImaginurl())),
                            cursor.getString(cursor.getColumnIndex(MyDb.getDbColumnTitle())),
                            cursor.getString(cursor.getColumnIndex(MyDb.getDbTime())),
                            cursor.getString(cursor.getColumnIndex(MyDb.getDbOrigin())), 0,
                            "",
                            cursor.getString(cursor.getColumnIndex(MyDb.getDbColumnUrl())));
                    newsArr.add(order);
                }
            }
        cursor.close();
        db.close();
        return newsArr;
    }

    public ArrayList<NewsItem> findSelectedAllT(long news_id, int index){
        SQLiteDatabase db = helper.getReadableDatabase();
        Cursor cursor = db.query(MyDb.getDbTableAll(index),
                MyDb.getDbColumnArray(),
                MyDb.getDbColumnNewsid() + " = ?",
                new String[] {String.valueOf(news_id)},
                null, null, null);
        Log.e("find db", String.valueOf(cursor.getCount()));
        ArrayList<NewsItem> newsArr = new ArrayList<NewsItem>(cursor.getCount());
        if (cursor.getCount() > 0) {
            while (cursor.moveToNext()) {
                //NewsItem(long index, String imageUrl, String title, String publishDate, String source, int readTimes, String preview, String contentUrl)
                NewsItem order = new NewsItem(cursor.getLong(cursor.getColumnIndex(MyDb.getDbColumnNewsid())),
                        cursor.getString(cursor.getColumnIndex(MyDb.getDbColumnImaginurl())),
                        cursor.getString(cursor.getColumnIndex(MyDb.getDbColumnTitle())),
                        cursor.getString(cursor.getColumnIndex(MyDb.getDbTime())),
                        cursor.getString(cursor.getColumnIndex(MyDb.getDbOrigin())), 0,
                        "",
                        cursor.getString(cursor.getColumnIndex(MyDb.getDbColumnUrl())));
                newsArr.add(order);
            }
        }
        cursor.close();
        db.close();
        return newsArr;
    }
}
