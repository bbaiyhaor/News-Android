package com.ihandy.a2014011419.myfragment;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.github.florent37.materialviewpager.header.MaterialViewPagerHeaderDecorator;
import com.ihandy.a2014011419.R;
import com.ihandy.a2014011419.myadapter.NewsRefreshItemAdapter;
import com.ihandy.a2014011419.news.NewsItem;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

import com.ihandy.a2014011419.parser.MyJsonParser;
import com.nispok.snackbar.Snackbar;

/**
 * Created by byr on 2016/9/1.
 */
public class NewsRefreshFragment extends Fragment {
    private static final int VISIBLE_THRESHOLD = 3;
    Unbinder ButterKnifeReset;
    private static final String STORE_PARAM = "param";
    @BindView(R.id.news_item)
    RecyclerView mRecyclerView;
    //  下拉刷新
    @BindView(R.id.swiperefreshlayout)
    SwipeRefreshLayout mSwipeRefreshLayout;

    NewsRefreshItemAdapter mAdapter;
    //  tab name
    private String mParam;
    //新闻列表数据
    private List<NewsItem> mnewsItemList = new ArrayList<NewsItem>();

    //获取 fragment 依赖的 Activity，方便使用 Context
    private Activity mActivity;

    //  上拉加载
    private boolean loading = false;

    public static Fragment newInstance(String param) {
        NewsRefreshFragment fragment = new NewsRefreshFragment();
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
        mRecyclerView.addItemDecoration(new MaterialViewPagerHeaderDecorator());
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_news_refresh, null);
        Log.i(STORE_PARAM, "in StoreFragment");
        mActivity = getActivity();
        ButterKnifeReset = ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //这里用线性显示 类似于listview
        mRecyclerView.setLayoutManager(new LinearLayoutManager(mActivity));
        //这里用线性宫格显示 类似于grid view
//        mRecyclerView.setLayoutManager(new GridLayoutManager(mActivity, 2));
        //这里用线性宫格显示 类似于瀑布流
//        mRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, OrientationHelper.VERTICAL));

        mAdapter = new NewsRefreshItemAdapter(mActivity, mnewsItemList);
        mRecyclerView.setAdapter(mAdapter);


        mRecyclerView.addOnScrollListener(new RecyclerView.OnScrollListener() {
            /**
             * Callback method to be invoked when the RecyclerView has been scrolled. This will be
             * called after the scroll has completed.
             * <p/>
             * This callback will also be called if visible item range changes after a layout
             * calculation. In that case, dx and dy will be 0.
             *
             * @param recyclerView The RecyclerView which scrolled.
             * @param dx           The amount of horizontal scroll.
             * @param dy           The amount of vertical scroll.
             */
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {

                super.onScrolled(recyclerView, dx, dy);


                LinearLayoutManager layoutManager = (LinearLayoutManager) recyclerView.getLayoutManager();

                int totalItemCount = layoutManager.getItemCount();

                int lastVisibleItem = layoutManager.findLastVisibleItemPosition();

                if (!loading && totalItemCount < (lastVisibleItem + VISIBLE_THRESHOLD)) {
                    Log.e("upload hehe", mAdapter.getTopItemId() + " " + mAdapter.getBottomItemId());
                    new newsItemTask(mActivity).execute(mAdapter.getBottomItemId());
                    loading = true;
                }
            }
        });




        mSwipeRefreshLayout.setColorSchemeColors(Color.RED, Color.BLUE);
        mSwipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.e("refresh hehe", mAdapter.getTopItemId() + " " + mAdapter.getBottomItemId());
                new MorenewsItemTask().execute(mAdapter.getTopItemId());
            }
        });

        mSwipeRefreshLayout.post(new Runnable() {
            @Override
            public void run() {
                mSwipeRefreshLayout.setRefreshing(true);
                new MorenewsItemTask().execute(mAdapter.getTopItemId());
            }
        });



    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnifeReset = null;
    }

    // Long 是输入参数 (ihandy 中的 news_id)
    class MorenewsItemTask extends AsyncTask<Long, Void, List<NewsItem>> {
        @Override
        protected List<NewsItem> doInBackground(Long... params) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getMoreById(mParam, params[0]);
        }

        @Override
        protected void onPostExecute(List<NewsItem> simplenewsItemItems) {
            super.onPostExecute(simplenewsItemItems);

            if (mSwipeRefreshLayout != null) {
                mSwipeRefreshLayout.setRefreshing(false);
            }
            //没有新的数据，提示消息
            if (simplenewsItemItems == null || simplenewsItemItems.size() == 0) {
                Snackbar.with(mActivity.getApplicationContext()) // context
                        .text(mActivity.getResources().getString(R.string.list_more_data)) // text to display
                        .duration(Snackbar.SnackbarDuration.LENGTH_SHORT) // make it shorter
                        .show(mActivity); // activity where it is displayed
            } else {
                mnewsItemList.clear();
                mnewsItemList.addAll(simplenewsItemItems);
                mAdapter.notifyDataSetChanged();
            }
        }
    }




    private List<NewsItem> getMoreById(String mParam, Long param) {
        ArrayList<NewsItem> more = new ArrayList<NewsItem>();
        MyJsonParser temp = new MyJsonParser(more, mActivity);
        temp.obtain(mParam);
        return more;

    }


    class newsItemTask extends AsyncTask<Long, Void, List<NewsItem>> {

        private Context mContext;

        public newsItemTask(Context context) {
            mContext = context;
        }

        /**
         * Runs on the UI thread before {@link #doInBackground}.
         */
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            if (mnewsItemList != null && mnewsItemList.size() > 0) {
                mnewsItemList.add(null);
                // notifyItemInserted(int position)，这个方法是在第position位置
                // 被插入了一条数据的时候可以使用这个方法刷新，
                // 注意这个方法调用后会有插入的动画，这个动画可以使用默认的，也可以自己定义。
                Log.e("up load", "in mnewsItemList.add(null)");
                mAdapter.notifyItemInserted(mnewsItemList.size() - 1);
            }
        }

        /**
         * @param params 偏移量 aid
         * @return
         */
        @Override
        protected List<NewsItem> doInBackground(Long... params) {
            try {
                Thread.sleep(1500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return getnewsItemList(mParam, params[0]);
        }

        @Override
        protected void onPostExecute(final List<NewsItem> morenewsItems) {
            // 新增新闻数据
            super.onPostExecute(morenewsItems);
            if (mnewsItemList.size() == 0) {
                mnewsItemList.addAll(morenewsItems);
                mAdapter.notifyDataSetChanged();
            } else {
                //删除 footer
                mnewsItemList.remove(mnewsItemList.size() - 1);
                mnewsItemList.addAll(morenewsItems);
                mAdapter.notifyDataSetChanged();
                loading = false;
            }
        }

    }

    /**
     * @param type   第几个栏目
     * @param max_news_id 偏移 aid
     * @return
     */
    public List<NewsItem> getnewsItemList(String type, long max_news_id) {
        ArrayList<NewsItem> more = new ArrayList<NewsItem>();
        MyJsonParser temp = new MyJsonParser(more, mActivity);
        temp.obtain(type, max_news_id);
        return more;
    }

}
