package com.ihandy.a2014011419.parser;

import android.content.Context;
import android.support.v4.app.Fragment;
import android.text.format.DateFormat;
import android.util.Log;

import com.ihandy.a2014011419.correspondence.HttpRequestKev;
import com.ihandy.a2014011419.mydb.DetailNewsOperation;
import com.ihandy.a2014011419.myfragment.NewsRefreshFragment;
import com.ihandy.a2014011419.news.NewsItem;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

/**
 * Created by byr on 2016/9/5.
 */
public class MyJsonParser {
    private ArrayList<String> channelList;
    private ArrayList<Fragment> newsFrag;
    private ArrayList<NewsItem> newsItems;
    private final String[] CHANNELDEFAULT = {"sports", "top_stories", "technology", "health", "world", "national", "entertainment"};
    private DetailNewsOperation news_db = null;


    public MyJsonParser(ArrayList<String> channelList, ArrayList<Fragment> newsFrag, Context context){
        this.channelList = channelList;
        this.newsFrag = newsFrag;
        news_db = new DetailNewsOperation(context);
    }

    public MyJsonParser(ArrayList<NewsItem> newsItems, Context context){
        this.newsItems = newsItems;
        news_db = new DetailNewsOperation(context);
    }

    public void obtain(){
        try {
            //在Logcat中将get到的信息打印出来
            Log.e("obtain 1", "http://assignment.crazz.cn/news/en/category?timestamp="+System.currentTimeMillis());
            //导入URL
            String body = HttpRequestKev.get("http://assignment.crazz.cn/news/en/category?timestamp="+System.currentTimeMillis()).body();
            //字符串转JSONObject, 但必须catch JSONException
            JSONObject jsonObject = new JSONObject(body);
                /* －－－ JSON数据处理 －－－ */
            //这里打印出的信息，是格式化的规整可读的信息，注意与body（）的不同
            Log.i("Json data", jsonObject.toString());
            //获取tab
            JSONObject categories = jsonObject.getJSONObject("data").getJSONObject("categories");
            Iterator it = categories.keys();
            while(it.hasNext()) {
                String tabS = (String)it.next();
                this.channelList.add(tabS);
                this.newsFrag.add(NewsRefreshFragment.newInstance(tabS));
                Log.e("TAB", tabS);
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }
    }

    private void obtainGet(String body, int index_){
        try {
            JSONObject jsonObject = new JSONObject(body);
            Log.e("obtain 3", jsonObject.toString());
            //  获取新闻信息
            JSONArray newsArr = jsonObject.getJSONObject("data").getJSONArray("news");
            int length = newsArr.length();
            if (length > 8)
                length = 8;
            for (int i = 0; i < length; i++){
                jsonObject = newsArr.getJSONObject(i);
                //  处理需要信息
                String imgUrl = "nop", sourceName = "nop", sourceUrl = "nop", title = "nop", origin = "nop", time = ((Long)System.currentTimeMillis()).toString();
                try {
                    long index = -1;
                    //  img
                    JSONArray imgArr = jsonObject.getJSONArray("imgs");
                    for (int j = 0; j < imgArr.length(); j++){
                        try {
                            imgUrl = imgArr.getJSONObject(j).getString("url");
                        }catch (Exception e){
                            e.printStackTrace();
                        }
                        Log.e("obtain imgurl", imgUrl);
                        break;
                    }
                    //  source
                    try {
                        JSONObject source = jsonObject.getJSONObject("source");
                        sourceName = source.getString("name");
                        sourceUrl = source.getString("url");
                    }catch (Exception e){
                        e.printStackTrace();
                    }
                    Log.e("obtain source", sourceName + "   " + sourceUrl);
                    //  index
                    index = Long.parseLong(jsonObject.getString("news_id"));
                    //  title
                    title = jsonObject.getString("title");
                    //  origin
                    origin = jsonObject.getString("origin");
                    //  time
                    try {
                        time = jsonObject.getString("updated_time");
                        Log.e("obtain raw time", time);
                        Log.e("Flag", ((Long) System.currentTimeMillis()).toString());
                        Log.e("obtain time", (String) DateFormat.format("yyyy-MM-dd", System.currentTimeMillis()));
                        time = (String) DateFormat.format("yyyy-MM-dd", Long.parseLong(time));
                        Log.e("obtain time", time);
                    }catch (Exception e){
                        time = jsonObject.getString("fetched_time");
                        Log.e("obtain raw time fetched", time);
                        Log.e("Flag fetched", ((Long) System.currentTimeMillis()).toString());
                        Log.e("obtain time fetched", (String) DateFormat.format("yyyy-MM-dd", System.currentTimeMillis()));
                        time = (String) DateFormat.format("yyyy-MM-dd", Long.parseLong(time));
                        Log.e("obtain time fetched", time);
                    }
                    NewsItem storeInfo = new NewsItem(index, imgUrl, title, time, origin, 1129,
                            "讲座主要内容：以中、西方音乐历史中经典音乐作品为基础，通过作曲家及作品创作背景、相关音乐文化史知识及音乐欣赏常识...", sourceUrl);
                    this.newsItems.add(storeInfo);

                    //  更新缓存
                    news_db.insertDetailNewsAllT(storeInfo, index_);
                }
                catch (Exception e){
                    e.printStackTrace();
                }finally {
                }
            }
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    public void obtain(String type, long max_news_id){
        Log.e("obtain type max_news_id","http://assignment.crazz.cn/news/query?locale=en&category=" + type + "&max_news_id=" + max_news_id);
        try {
            String body = HttpRequestKev.get("http://assignment.crazz.cn/news/query?locale=en&category=" + type + "&max_news_id=" + max_news_id).body();
            int index = -1;
            for (int i = 0; i < CHANNELDEFAULT.length; i++)
                if (CHANNELDEFAULT[i].equals(type)){
                    index = i;
                    break;
                }
            obtainGet(body, index);
        }catch (Exception e){
            e.printStackTrace();
        }
        if (this.newsItems.size() > 0){
            this.newsItems.remove(0);
        }
    }

    public void obtain(String type){
        Log.e("obtain type","http://assignment.crazz.cn/news/query?locale=en&category=" + type);
        int index = -1;
        for (int i = 0; i < CHANNELDEFAULT.length; i++)
            if (CHANNELDEFAULT[i].equals(type)){
                index = i;
                break;
            }
        String body = null;
        try {
            body = HttpRequestKev.get("http://assignment.crazz.cn/news/query?locale=en&category=" + type).body();

            obtainGet(body, index);
        }catch (Exception e){
            e.printStackTrace();
        }

        try {
            if (this.newsItems.size() == 0){
                ArrayList<NewsItem> temp = news_db.findAllAllT(index);
                Log.e("no net", String.valueOf(temp.size()));
                int length = temp.size();
                if (length > 15)
                    length = 15;
                for (int i = 0; i < length; i++)
                    this.newsItems.add(temp.get(i));
            }
            else
                Log.e("have net", String.valueOf(this.newsItems.size()));
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}
