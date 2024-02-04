package com.beknumonov.noteapp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.LongDef;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.beknumonov.noteapp2.base.BaseActivity;
import com.beknumonov.noteapp2.databinding.ActivitySplashBinding;
import com.beknumonov.noteapp2.model.News;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.google.gson.Gson;

public class SplashActivity extends BaseActivity<ActivitySplashBinding> {
    @Override
    protected ActivitySplashBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivitySplashBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        if (getIntent().hasExtra("news")) {
            Log.d("news", getIntent().getStringExtra("news"));
            String news_json = getIntent().getStringExtra("news");
            News news = new Gson().fromJson(news_json, News.class);
            if (news != null) {
                Intent intent = new Intent(SplashActivity.this, NewsDetailsActivity.class);
                intent.putExtra("news", news);
                startActivity(intent);
            }

        }


        boolean isLoggedIn = (boolean) preferencesManager.getValue(Boolean.class, "isLoggedIn", false);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isLoggedIn) {
                    Intent intent = new Intent(SplashActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    Intent intent = new Intent(SplashActivity.this, LoginActivity.class);
                    startActivity(intent);
                }
                finish();
            }
        }, 3000);
    }
}
