package com.ihandy.a2014011419.myadapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ihandy.a2014011419.R;
import com.ihandy.a2014011419.news.NewsDetail;
import com.ihandy.a2014011419.news.NewsItem;
//import com.facebook.drawee.view.SimpleDraweeView;
import com.ihandy.a2014011419.view.WrapContentDraweeView;
import com.pnikosis.materialishprogress.ProgressWheel;


import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;



/**
 * Created by byr on 2016/9/1.
 */
public class NewsRefreshItemAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //  正常的普通一条新闻
    //  头条新闻size大一点
    static final int TYPE_HEADER = 0;
    //  第二条新闻size小一点
    static final int TYPE_CELL = 1;

    //  上拉加载更多
    //底部--往往是loading_more
    public final static int TYPE_FOOTER = 2;
    //新闻列表
    private List<NewsItem> newsItemList;

    //context
    private Context context;

    private LayoutInflater mLayoutInflater;


    public NewsRefreshItemAdapter(Context context, List<NewsItem> newsItemList) {
        this.context = context;
        if (newsItemList == null) {
            this.newsItemList = new ArrayList<NewsItem>();
        } else {
            this.newsItemList = newsItemList;
        }
        mLayoutInflater = LayoutInflater.from(context);
    }


    /**
     * 传进来的 List 末尾增加一个 null
     * null 作为是上拉的 progressBar 的标记
     * http://android-pratap.blogspot.com/2015/06/endless-recyclerview-with-progress-bar.html
     * 参看的这篇文章
     *
     //  根据位置判断是否是头条新闻
     //  根据位置判断是否加载更多
     * @param position
     * @return
     */
    @Override
    public int getItemViewType(int position) {
        NewsItem newsItem = newsItemList.get(position);
        //  footer
        if (newsItem == null)
            return TYPE_FOOTER;
        //  normal
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }

    //返回最后一个文章index，为了上拉刷新从该index开始加载过去的新闻
    public long getBottomItemId() {
        if (newsItemList == null || newsItemList.size() == 0 || newsItemList.get(newsItemList.size() - 1) == null)//  避免下拉上拉同时进行
            return -1;
        return newsItemList.get(newsItemList.size() - 1).getIndex();
    }

    //返回第一个文章index，为了下拉刷新从该index开始加载更新的新闻
    public long getTopItemId() {
        if (newsItemList == null || newsItemList.size() == 0 || newsItemList.get(0) == null)//  避免下拉上拉同时进行
            return -1;
        return newsItemList.get(0).getIndex();
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_HEADER: {
                view = mLayoutInflater.inflate(
                        R.layout.my_news_item_big, parent, false);
                return new NewsItemViewHolder(view);
            }
            case TYPE_CELL: {
                view = mLayoutInflater.inflate(
                        R.layout.my_news_item_small, parent, false);
                return new NewsItemViewHolder(view);
            }
            case TYPE_FOOTER:
                view = mLayoutInflater.inflate(
                        R.layout.recyclerview_footer, parent, false);
                return new FooterViewHolder(view);
        }
        Log.e("refresh", "holder bug!!!");
        return null;
    }

    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        //这时候 newsItem是 null，先把 footer 处理了
        if (holder instanceof FooterViewHolder) {
            ((FooterViewHolder) holder).rcvLoadMore.spin();
            return;
        }

        NewsItemViewHolder NewsItemHolder = (NewsItemViewHolder)holder;
        final NewsItem newsItem = newsItemList.get(position);
        NewsItemHolder.newsItemPhoto.setImageURI(Uri.parse(newsItem.getImageUrl()));
        NewsItemHolder.newsItemTitle.setText(newsItem.getTitle());
        NewsItemHolder.newsItemDate.setText(newsItem.getPublishDate());
        NewsItemHolder.newsItemSource.setText(newsItem.getSource());
        //注意这个阅读次数是 int 类型，需要转化为 String 类型
        //holder.newsItemReadtimes.setText(newsItem.getReadTimes()+"次");
        NewsItemHolder.newsItemPreview.setText(newsItem.getPreview());


        NewsItemHolder.cardView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                NewsDetail.showDetail((Activity)context, newsItem);
            }
        });
    }


    @Override
    public int getItemCount() {
        if (newsItemList != null) {
            return newsItemList.size();
        } else {
            return 0;
        }
    }



    //  普通viewholder
    class NewsItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_item_photo)
        WrapContentDraweeView newsItemPhoto;
        @BindView(R.id.news_item_title)
        TextView newsItemTitle;
        @BindView(R.id.news_item_date)
        TextView newsItemDate;
        @BindView(R.id.news_item_source)
        TextView newsItemSource;
      /*  @BindView(R.id.news_item_readtimes)
        TextView newsItemReadtimes;*/
        @BindView(R.id.news_item_preview)
        TextView newsItemPreview;
        @BindView(R.id.card_item)
        CardView cardView;


        public NewsItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }




    /**
     * 底部加载更多
     */
    class FooterViewHolder extends RecyclerView.ViewHolder {
        @BindView(R.id.rcv_load_more)
        ProgressWheel rcvLoadMore;

        public FooterViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}