package com.ihandy.a2014011419.myfragment;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.format.DateFormat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.ihandy.a2014011419.correspondence.HttpRequestKev;
import com.ihandy.a2014011419.news.NewsItem;
import com.ihandy.a2014011419.R;
import com.ihandy.a2014011419.myadapter.NewsItemAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by byr on 2016/9/1.
 */
public class NewsFragment extends Fragment {
    Unbinder ButterKnifeReset;
    private static final String STORE_PARAM = "param";
    @BindView(R.id.news_item)
    RecyclerView newsnewsItem;

    private String mParam;
    //新闻列表数据
    private List<NewsItem> itemnewsItemList = new ArrayList<NewsItem>();

    //获取 fragment 依赖的 Activity，方便使用 Context
    private Activity mAct;


    public static Fragment newInstance(String param) {
        NewsFragment fragment = new NewsFragment();
        Bundle args = new Bundle();
        args.putString(STORE_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam = getArguments().getString(STORE_PARAM);
        }
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        newsnewsItem.addItemDecoration(new MaterialViewPagerHeaderDecorator());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news, null);
        Log.i(STORE_PARAM, "in StoreFragment");
        mAct = getActivity();
        ButterKnifeReset = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //这里用线性显示 类似于listview
        newsnewsItem.setLayoutManager(new LinearLayoutManager(mAct));
        //这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new GridLayoutManager(mAct, 2));
        //这里用线性宫格显示 类似于瀑布流
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));

        new ObtainNewsTask().execute();

    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
//        ButterKnife.reset(this);
        ButterKnifeReset = null;
    }

    //1.Android不允许在主线程（对于android来说，主线程就是UI线程）中访问网络。
    //2.Android不允许在一个子线程中直接去更新主线程中的UI控件。
    //  后台获取信息
    class ObtainNewsTask extends AsyncTask<String, Void, List<NewsItem>> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
        }

        private void obtain() {
            try {

                Log.e("obtain 2","http://assignment.crazz.cn/news/query?locale=en&category="+mParam);
                String body = HttpRequestKev.get("http://assignment.crazz.cn/news/query?locale=en&category="+mParam).body();
                JSONObject jsonObject = new JSONObject(body);
                Log.e("obtain 3", jsonObject.toString());
                //  获取新闻信息

                JSONArray newsArr = jsonObject.getJSONObject("data").getJSONArray("news");

                for (int i = 0; i < newsArr.length(); i++){
                    jsonObject = newsArr.getJSONObject(i);
                    //  处理需要信息
                    String imgUrl = "nop", sourceName = "nop", sourceUrl = "nop", title = "nop", origin = "nop", time = ((Long)System.currentTimeMillis()).toString();
                    try {
                        long index = -1;
                        //  img
                        JSONArray imgArr = jsonObject.getJSONArray("imgs");
                        for (int j = 0; j < imgArr.length(); j++){
                            imgUrl = imgArr.getJSONObject(j).getString("url");
                            Log.e("obtain imgurl", imgUrl);
                            break;
                        }
                        //  source
                        JSONObject source = jsonObject.getJSONObject("source");
                        sourceName = source.getString("name");
                        sourceUrl = source.getString("url");
                        Log.e("obtain source", sourceName + "   " + sourceUrl);
                        //  index
                        index = Long.parseLong(jsonObject.getString("news_id"));
                        //  title
                        title = jsonObject.getString("title");
                        //  origin
                        origin = jsonObject.getString("origin");
                        //  time
                        time = jsonObject.getString("updated_time");
                        Log.e("obtain time", time);
                        Log.e("Flag", ((Long)System.currentTimeMillis()).toString());
                        Log.e("obtain time", (String) DateFormat.format("yyyy-MM-dd", System.currentTimeMillis()));
                        time = (String) DateFormat.format("yyyy-MM-dd",Long.parseLong(time));

                        Log.e("obtain time", time);
                        NewsItem storeInfo = new NewsItem(index, imgUrl, title, time, origin, 1129,
                                "讲座主要内容：以中、西方音乐历史中经典音乐作品为基础，通过作曲家及作品创作背景、相关音乐文化史知识及音乐欣赏常识...", sourceUrl);
                        itemnewsItemList.add(storeInfo);
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
        @Override
        protected List<NewsItem> doInBackground(String... params) {
            obtain();
            if (itemnewsItemList.size() == 0){
                NewsItem storeInfo1 =
                        new NewsItem(20123, "http://i2.sinaimg.cn/ent/j/2012-05-20/U5912P28T3D3634984F328DT20120520152700.JPG", "关于举办《经典音乐作品欣赏与人文审美》讲座的通知", "2015-01-09", "科学研究院", 1129,
                                "讲座主要内容：以中、西方音乐历史中经典音乐作品为基础，通过作曲家及作品创作背景、相关音乐文化史知识及音乐欣赏常识...", "http://www.baidu.com");
                NewsItem storeInfo2 =
                        new NewsItem(20123, "http://i2.sinaimg.cn/ent/j/2012-05-20/U5912P28T3D3634984F328DT20120520152700.JPG", "关于举办《经典音乐作品欣赏与人文审美》讲座的通知", "2015-01-09", "科学研究院", 1129,
                                "讲座主要内容：以中、西方音乐历史中经典音乐作品为基础，通过作曲家及作品创作背景、相关音乐文化史知识及音乐欣赏常识...", "http://www.baidu.com");
                NewsItem storeInfo3 =
                        new NewsItem(20123, "http://i2.sinaimg.cn/ent/j/2012-05-20/U5912P28T3D3634984F328DT20120520152700.JPG", "关于举办《经典音乐作品欣赏与人文审美》讲座的通知", "2015-01-09", "科学研究院", 1129,
                                "讲座主要内容：以中、西方音乐历史中经典音乐作品为基础，通过作曲家及作品创作背景、相关音乐文化史知识及音乐欣赏常识...", "http://www.baidu.com");
                NewsItem storeInfo4 =
                        new NewsItem(20123, "http://i2.sinaimg.cn/ent/j/2012-05-20/U5912P28T3D3634984F328DT20120520152700.JPG", "关于举办《经典音乐作品欣赏与人文审美》讲座的通知", "2015-01-09", "科学研究院", 1129,
                                "讲座主要内容：以中、西方音乐历史中经典音乐作品为基础，通过作曲家及作品创作背景、相关音乐文化史知识及音乐欣赏常识...", "http://www.baidu.com");
                NewsItem storeInfo5 =
                        new NewsItem(20123, "http://i2.sinaimg.cn/ent/j/2012-05-20/U5912P28T3D3634984F328DT20120520152700.JPG", "关于举办《经典音乐作品欣赏与人文审美》讲座的通知", "2015-01-09", "科学研究院", 1129,
                                "讲座主要内容：以中、西方音乐历史中经典音乐作品为基础，通过作曲家及作品创作背景、相关音乐文化史知识及音乐欣赏常识...", "http://www.baidu.com");
                NewsItem storeInfo6 =
                        new NewsItem(20123, "http://i2.sinaimg.cn/ent/j/2012-05-20/U5912P28T3D3634984F328DT20120520152700.JPG", "关于举办《经典音乐作品欣赏与人文审美》讲座的通知", "2015-01-09", "科学研究院", 1129,
                                "讲座主要内容：以中、西方音乐历史中经典音乐作品为基础，通过作曲家及作品创作背景、相关音乐文化史知识及音乐欣赏常识...", "http://www.baidu.com");
                itemnewsItemList.add(storeInfo1);
                itemnewsItemList.add(storeInfo2);
                itemnewsItemList.add(storeInfo3);
                itemnewsItemList.add(storeInfo4);
                itemnewsItemList.add(storeInfo5);
                itemnewsItemList.add(storeInfo6);
            }
            return itemnewsItemList;
        }



        @Override
        protected void onPostExecute(List<NewsItem> data) {
            super.onPostExecute(data);
            NewsItemAdapter adapter = new NewsItemAdapter(mAct, data);
            newsnewsItem.setAdapter(adapter);
        }
    }

}
