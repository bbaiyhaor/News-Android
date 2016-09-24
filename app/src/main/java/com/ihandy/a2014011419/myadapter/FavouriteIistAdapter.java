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
import com.ihandy.a2014011419.view.WrapContentDraweeView;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

//import com.facebook.drawee.view.SimpleDraweeView;


/**
 * Created by byr on 2016/9/1.
 */
public class FavouriteIistAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    //新闻列表
    private List<NewsItem> newsItemList;

    //context
    private Context context;

    private LayoutInflater mLayoutInflater;


    public FavouriteIistAdapter(Context context, List<NewsItem> newsItemList) {
        this.context = context;
        if (newsItemList == null) {
            Log.e("fav null", "constructor");
            this.newsItemList = new ArrayList<NewsItem>();
        } else {
            this.newsItemList = newsItemList;
            Log.e("fav hava", String.valueOf(newsItemList.size()));
        }
        mLayoutInflater = LayoutInflater.from(context);
    }


    //  设置需要重复的 view
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Log.e("fav createviewholder", "woqu");
        View view = mLayoutInflater.inflate(
                        R.layout.favourite_item, parent, false);
        return new NewsItemViewHolder(view);
    }

    //  设置 position 的 item
    @Override
    public void onBindViewHolder(final RecyclerView.ViewHolder holder, int position) {
        Log.e("fav bindViewHolder", "madan");
        NewsItemViewHolder NewsItemHolder = (NewsItemViewHolder)holder;
        final NewsItem newsItem = newsItemList.get(position);
        NewsItemHolder.favouriteItemPhoto.setImageURI(Uri.parse(newsItem.getImageUrl()));
        NewsItemHolder.favouriteItemTitle.setText(newsItem.getTitle());
        NewsItemHolder.favouriteItemDate.setText(newsItem.getPublishDate());
        NewsItemHolder.favouriteItemSource.setText(newsItem.getSource());
        NewsItemHolder.cardView.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                NewsDetail.showDetail((Activity)context, newsItem);
            }
        });
    }

    //  设置重复个数
    @Override
    public int getItemCount() {
        Log.e("fav ", String.valueOf(newsItemList.size()));
        if (newsItemList != null) {
            return newsItemList.size();
        } else {
            return 0;
        }
    }



    class NewsItemViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.favourite_item_photo)
        WrapContentDraweeView favouriteItemPhoto;
        @BindView(R.id.favourite_item_title)
        TextView favouriteItemTitle;
        @BindView(R.id.favourite_item_date)
        TextView favouriteItemDate;
        @BindView(R.id.favourite_item_source)
        TextView favouriteItemSource;
        @BindView(R.id.favourite_item)
        CardView cardView;


        public NewsItemViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }

}