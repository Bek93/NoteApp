package com.beknumonov.noteapp2.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.beknumonov.noteapp2.NewsDetailsActivity;
import com.beknumonov.noteapp2.base.BaseRecyclerAdapter;
import com.beknumonov.noteapp2.base.BaseViewHolder;
import com.beknumonov.noteapp2.base.ListLoadingListener;
import com.beknumonov.noteapp2.databinding.ItemLoadingBinding;
import com.beknumonov.noteapp2.databinding.ItemNewsBinding;
import com.beknumonov.noteapp2.model.News;
import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.ArrayList;

public class NewsListAdapter extends BaseRecyclerAdapter {

    private ArrayList<News> newsArrayList;

    private static int ITEM_NEWS_VIEW_TYPE = 0;
    private static int ITEM_LOADING_VIEW_TYPE = 1;
    private ListLoadingListener listener;

    public void setListener(ListLoadingListener listener) {
        this.listener = listener;
    }

    public NewsListAdapter(ArrayList<News> newsArrayList) {
        this.newsArrayList = newsArrayList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        if (viewType == ITEM_NEWS_VIEW_TYPE) {
            ItemNewsBinding binding = ItemNewsBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new NewsViewHolder(binding);
        } else {
            ItemLoadingBinding binding = ItemLoadingBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
            return new LoadingViewHolder(binding);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        if (holder instanceof LoadingViewHolder) {
            if (listener != null) {
                listener.onLoadingCreated();
            }
        }
    }

    @Override
    public int getItemViewType(int position) {
        // check if item in position is News or Loading
        if (position < newsArrayList.size())
            return ITEM_NEWS_VIEW_TYPE;
        else return ITEM_LOADING_VIEW_TYPE;
    }

    @Override
    public int getItemCount() {
        if (newsArrayList.size() > 0)
            return newsArrayList.size() + 1;
        else return 0;
    }

    class NewsViewHolder extends BaseViewHolder<ItemNewsBinding> {

        public NewsViewHolder(ItemNewsBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            News news = newsArrayList.get(position);
            binding.newsTitle.setText(news.getTitle());
            binding.newsSource.setText(news.getSource());
            binding.newsContent.setText(news.getContent());

            RequestOptions options = new RequestOptions();
            options.centerCrop();

            Glide.with(binding.newsImageView).load(news.getImage_url()).into(binding.newsImageView);

            binding.parent.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(binding.parent.getContext(), NewsDetailsActivity.class);
                    intent.putExtra("news", newsArrayList.get(position));
                    binding.parent.getContext().startActivity(intent);
                }
            });
        }
    }

    class LoadingViewHolder extends BaseViewHolder<ItemLoadingBinding> {

        public LoadingViewHolder(ItemLoadingBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {

        }
    }


}
