package com.ihandy.a2014011419.myadapter;

import android.app.Activity;
import android.content.Context;
import android.net.Uri;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import com.ihandy.a2014011419.news.NewsDetail;
//import com.facebook.drawee.view.SimpleDraweeView;
import com.ihandy.a2014011419.view.WrapContentDraweeView;
import com.ihandy.a2014011419.news.NewsItem;
import com.ihandy.a2014011419.R;
import java.util.List;
import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by byr on 2016/9/1.
 */
public class NewsItemAdapter extends RecyclerView.Adapter<NewsItemAdapter.NewsItemViewHolder> {

    //  头条新闻size大一点
    static final int TYPE_HEADER = 0;
    //  第二条新闻size小一点
    static final int TYPE_CELL = 1;

    //新闻列表
    private List<NewsItem> newsItemList;

    //context
    private Context context;

    private LayoutInflater mLayoutInflater;


    public NewsItemAdapter(Context context, List<NewsItem> newsItemList) {
        this.context = context;
        this.newsItemList = newsItemList;
        mLayoutInflater = LayoutInflater.from(context);
    }


    //根据位置判断是否是头条新闻
    @Override
    public int getItemViewType(int position) {
        switch (position) {
            case 0:
                return TYPE_HEADER;
            default:
                return TYPE_CELL;
        }
    }


    @Override
    public NewsItemViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = null;
        switch (viewType) {
            case TYPE_HEADER: {
                view = mLayoutInflater.inflate(
                        R.layout.my_news_item_big, parent, false);
                break;
            }
            case TYPE_CELL: {
                view = mLayoutInflater.inflate(
                        R.layout.my_news_item_small, parent, false);
                break;
            }
        }
        return new NewsItemViewHolder(view);
    }

    @Override
    public void onBindViewHolder(NewsItemViewHolder holder, int position) {
        final NewsItem newsItem = newsItemList.get(position);
        holder.newsnewsItemPhoto.setImageURI(Uri.parse(newsItem.getImageUrl()));
        holder.newsnewsItemTitle.setText(newsItem.getTitle());
        holder.newsnewsItemDate.setText(newsItem.getPublishDate());
        holder.newsnewsItemSource.setText(newsItem.getSource());
        //注意这个阅读次数是 int 类型，需要转化为 String 类型
        //holder.newsItemReadtimes.setText(newsItem.getReadTimes()+"次");
        holder.newsnewsItemPreview.setText(newsItem.getPreview());


        holder.cardView.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                NewsDetail.showDetail((Activity)context, newsItem);

            }
        });
    }


    @Override
    public int getItemCount() {
        return newsItemList.size();
    }

    class NewsItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.news_item_photo)
        WrapContentDraweeView newsnewsItemPhoto;
        @BindView(R.id.news_item_title)
        TextView newsnewsItemTitle;
        @BindView(R.id.news_item_date)
        TextView newsnewsItemDate;
        @BindView(R.id.news_item_source)
        TextView newsnewsItemSource;
        @BindView(R.id.news_item_preview)
        TextView newsnewsItemPreview;
        @BindView(R.id.card_item)
        CardView cardView;


        public NewsItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}