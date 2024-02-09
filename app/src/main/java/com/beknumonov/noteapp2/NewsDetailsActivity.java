package com.beknumonov.noteapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.Nullable;

import com.beknumonov.noteapp2.base.BaseActivity;
import com.beknumonov.noteapp2.databinding.ActivityNewsDetailsBinding;
import com.beknumonov.noteapp2.model.News;
import com.bumptech.glide.Glide;

public class NewsDetailsActivity extends BaseActivity<ActivityNewsDetailsBinding> {
    private News news;

    @Override
    protected ActivityNewsDetailsBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityNewsDetailsBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("News detail");
        news = (News) getIntent().getSerializableExtra("news");


        if (news != null) {
            binding.title.setText(news.getTitle());
            binding.content.setText(news.getContent());
            Glide.with(this)
                    .load(news.getImage_url())
                    .placeholder(R.drawable.baseline_add_to_photos_24)
                    .into(binding.newsImageView);
        }

    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        news = (News) getIntent().getSerializableExtra("news");
        if (news != null) {
            binding.title.setText(news.getTitle());
            binding.content.setText(news.getContent());
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.news_details_menu, menu);
        return super.onCreateOptionsMenu(menu);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.share) {
            Intent intent = new Intent(this, UserListActivity.class);
            intent.putExtra("news_id", news.getId());
            startActivity(intent);
            return true;
        } else {
            return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public boolean hasBackButton() {
        return true;
    }

}
