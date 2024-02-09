package com.beknumonov.noteapp2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.beknumonov.noteapp2.adapters.NewsListAdapter;
import com.beknumonov.noteapp2.base.BaseFragment;
import com.beknumonov.noteapp2.base.ListLoadingListener;
import com.beknumonov.noteapp2.databinding.FragmentNewsBinding;
import com.beknumonov.noteapp2.model.News;
import com.beknumonov.noteapp2.model.Note;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NewsFragment extends BaseFragment<FragmentNewsBinding> {

    private ArrayList<News> newsArrayList;
    private NewsListAdapter adapter;
    private int currentPage = 1;

    @Override
    protected FragmentNewsBinding inflateViewBinding(LayoutInflater inflater, ViewGroup parent, boolean toAttachRoot) {
        return FragmentNewsBinding.inflate(inflater, parent, toAttachRoot);
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        newsArrayList = new ArrayList<>();
        adapter = new NewsListAdapter(newsArrayList);
        adapter.setListener(new ListLoadingListener() {
            @Override
            public void onLoadingCreated() {
                loadNews(currentPage);
            }
        });
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.newsRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false));
        binding.newsRecyclerView.setAdapter(adapter);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                currentPage = 1;
                newsArrayList.clear();
                adapter.notifyDataSetChanged();
                loadNews(currentPage);
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();
        loadNews(currentPage);

    }

    private void loadNews(int page) {


        if (page == 1 && !binding.swipeRefreshLayout.isRefreshing()) parent.showLoading();
        Call<ArrayList<News>> call = parent.mainApi.getNews(page);
        call.enqueue(new Callback<ArrayList<News>>() {
            @Override
            public void onResponse(Call<ArrayList<News>> call, Response<ArrayList<News>> response) {
                parent.hideLoading();
                if (binding.swipeRefreshLayout.isRefreshing()) {
                    binding.swipeRefreshLayout.setRefreshing(false);
                }
                if (response.isSuccessful()) {
                    ArrayList<News> news = response.body();
                    if (news.size() > 0) {
                        if (news.size() == 10) {
                            currentPage++;
                        }
                        newsArrayList.addAll(news);
                        adapter.notifyDataSetChanged();
                    }
                }
            }

            @Override
            public void onFailure(Call<ArrayList<News>> call, Throwable t) {
                parent.hideLoading();
                if (binding.swipeRefreshLayout.isRefreshing()) {
                    binding.swipeRefreshLayout.setRefreshing(false);
                }

            }
        });


    }


}
