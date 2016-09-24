package com.ihandy.a2014011419.activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import com.ihandy.a2014011419.R;
import com.ihandy.a2014011419.mydb.DetailNewsOperation;
import com.ihandy.a2014011419.news.NewsItem;
import com.thefinestartist.finestwebview.FinestWebViewActivity;

import java.util.List;

/**
 * Created by byr on 2016/9/3.
 */
public class SubFwa extends FinestWebViewActivity {
    private DetailNewsOperation news_db;
    private NewsItem thisNews;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        news_db = new DetailNewsOperation(this);

        Intent getIntent = getIntent();
        String index = getIntent.getStringExtra("_index");
        String imageUrl = getIntent.getStringExtra("_imageUrl");
        String title = getIntent.getStringExtra("_title");
        String time = getIntent.getStringExtra("_time");
        String source = getIntent.getStringExtra("_source");
        String url = getIntent.getStringExtra("_url");

        Log.e("subfwa init", index + " " + imageUrl + " " + title + " " + time + " " + source + " " + url);
        //NewsItem(long index, String imageUrl, String title, String publishDate, String source, int readTimes, String preview, String contentUrl)
        thisNews = new NewsItem(Long.parseLong(index), imageUrl, title, time, source, 0, "", url);

        List<NewsItem> newsArr = news_db.findSelected(thisNews.getIndex());
        if (newsArr == null || newsArr.size() == 0){
            ImageView fav = (ImageView) findViewById(R.id.menufavouriteTv);
            fav.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_star_white));
        }
        else {
            ImageView fav = (ImageView) findViewById(R.id.menufavouriteTv);
            fav.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_star_black));
        }

    }

    private void changeFavourite() {
        List<NewsItem> newsArr = news_db.findSelected(thisNews.getIndex());
        if (newsArr == null || newsArr.size() == 0){
            ImageView fav = (ImageView) findViewById(R.id.menufavouriteTv);
            fav.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_star_black));
            news_db.insertDetailNews(thisNews);
        }
        else {
            ImageView fav = (ImageView) findViewById(R.id.menufavouriteTv);
            fav.setImageDrawable(this.getResources().getDrawable(R.drawable.ic_star_white));
            news_db.del(thisNews.getIndex());
        }
    }


    @Override public void onClick(View v) {
        int viewId = v.getId();
        Log.e("view " + viewId, "expected   " + R.id.menufavourite + " " + R.id.menufavouriteTv);
        if (viewId == R.id.menufavourite){
            changeFavourite();
        }else
        if (viewId == com.thefinestartist.finestwebview.R.id.menuShareVia) {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, webView.getUrl());
        //    sendIntent.setType("text/plain");
            sendIntent.setType("image/*");
            sendIntent.putExtra(Intent.EXTRA_STREAM, thisNews.getImageUrl());
            startActivity(Intent.createChooser(sendIntent, getResources().getString(stringResShareVia)));
            hideMenu();
        }else
           super.onClick(v);
    }

}
