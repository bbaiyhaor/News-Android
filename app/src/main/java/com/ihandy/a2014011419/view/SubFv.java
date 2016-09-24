package com.ihandy.a2014011419.view;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.StringRes;
import android.util.Log;

import com.ihandy.a2014011419.activity.SubFwa;
import com.ihandy.a2014011419.news.NewsItem;
import com.thefinestartist.finestwebview.FinestWebView;
import com.thefinestartist.finestwebview.listeners.BroadCastManager;
import com.thefinestartist.utils.content.Ctx;
import com.thefinestartist.utils.content.Res;

/**
 * Created by byr on 2016/9/3.
 */
public class SubFv extends FinestWebView {
    private static NewsItem thisNews;
    public static void init(NewsItem thatNews){
        thisNews = thatNews;
    }
    public static NewsItem getThisNews(){
        return thisNews;
    }
    public static class SubBuilder extends Builder {

        public SubBuilder(@NonNull Activity activity) {
            super(activity);
        }

        public SubBuilder(@NonNull Context context) {
            super(context);

        }
        public void show(NewsItem thisNews){
            Log.e("SubFv new show", "enter");
            init(thisNews);
            show(thisNews.getContentUrl());
        }

        @Override
        public void show(@StringRes int urlRes) {
            show(Res.getString(urlRes));
        }

        @Override
        public void show(@NonNull String url) {
            show(url, null);
        }

        @Override
        protected void show(String url, String data) {
            this.url = url;
            this.data = data;
            this.key = System.identityHashCode(this);

            if (!listeners.isEmpty()) new BroadCastManager(context, key, listeners);

            Intent intent = new Intent(context, SubFwa.class);
            intent.putExtra("builder", this);
            Log.e("SubFv show", thisNews.getTitle());
            intent.putExtra("_index", String.valueOf(thisNews.getIndex()));
            intent.putExtra("_imageUrl", thisNews.getImageUrl());
            intent.putExtra("_title", thisNews.getTitle());
            intent.putExtra("_time", thisNews.getPublishDate());
            intent.putExtra("_source", thisNews.getSource());
            intent.putExtra("_url", thisNews.getContentUrl());
            Ctx.startActivity(intent);

            if (context instanceof Activity) {
                ((Activity) context).overridePendingTransition(animationOpenEnter, animationOpenExit);
            }
        }
    }


}
