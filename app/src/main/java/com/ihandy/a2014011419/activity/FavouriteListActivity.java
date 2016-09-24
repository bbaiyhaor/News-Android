package com.ihandy.a2014011419.activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.ihandy.a2014011419.R;
import com.ihandy.a2014011419.myadapter.FavouriteIistAdapter;
import com.ihandy.a2014011419.mydb.DetailNewsOperation;
import com.ihandy.a2014011419.news.NewsItem;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

public class FavouriteListActivity extends AppCompatActivity {


    @BindView(R.id.favourite_item_list)
    RecyclerView mRecyclerViewList;


    ArrayList<NewsItem> favouriteData = new ArrayList<NewsItem>();
    private DetailNewsOperation news_db;
    private FavouriteIistAdapter mAdapter;
    private Unbinder ButterKnifeReset;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite_list);
        news_db = new DetailNewsOperation(this);
        //  绑定 butterKnife
        ButterKnifeReset = ButterKnife.bind(this);
        Log.e("favourite oncreate", "zhale");
        initView();
    }

    private void initView() {
        mRecyclerViewList.setLayoutManager(new LinearLayoutManager(this));
        //这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        //这里用线性宫格显示 类似于瀑布流
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));
        mAdapter = new FavouriteIistAdapter(this, favouriteData);
        mRecyclerViewList.setAdapter(mAdapter);
        Log.e("initView", "ok");
    }

    @Override
    public void onResume() {
        super.onResume();
        initData();
    }

    private void initData() {
        Log.e("initData", "ok");
        //  不能直接写成 favoriteData = news_db.findAll();
        //  因为他们是共享一块内存,这样他们共享的内存实际上并没有改变
        ArrayList<NewsItem> getData = news_db.findAll();
        favouriteData.clear();
        favouriteData.addAll(getData);
        for (int i = 0; i < favouriteData.size(); i++)
            Log.e("data news", String.valueOf(favouriteData.get(i).getIndex()));
        mRecyclerViewList.getAdapter().notifyDataSetChanged();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        ButterKnifeReset = null;
    }
}
