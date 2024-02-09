package com.beknumonov.noteapp2;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.beknumonov.noteapp2.adapters.UserListAdapter;
import com.beknumonov.noteapp2.base.BaseActivity;
import com.beknumonov.noteapp2.databinding.ActivityUserListBinding;
import com.beknumonov.noteapp2.model.User;
import com.google.gson.JsonObject;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserListActivity extends BaseActivity<ActivityUserListBinding> {
    @Override
    protected ActivityUserListBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityUserListBinding.inflate(inflater);
    }

    private UserListAdapter adapter;

    private ArrayList<User> userArrayList = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        adapter = new UserListAdapter(userArrayList);

        adapter.setUserSelectListener(new UserListAdapter.UserSelectListener() {
            @Override
            public void onSelected(User user) {
                shareNews(user);
            }
        });
        binding.userRecyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        binding.userRecyclerView.setAdapter(adapter);
        String access_token = "Bearer " + preferencesManager.getValue(String.class, "access_token", "");
        Call<ArrayList<User>> call = mainApi.getUserList();

        call.enqueue(new Callback<ArrayList<User>>() {
            @Override
            public void onResponse(Call<ArrayList<User>> call, Response<ArrayList<User>> response) {
                userArrayList.clear();
                if (response.body() != null)
                    userArrayList.addAll(response.body());
                adapter.notifyDataSetChanged();

                Log.d("News", userArrayList.toString());
            }

            @Override
            public void onFailure(Call<ArrayList<User>> call, Throwable t) {

            }
        });
    }

    private void shareNews(User user) {
        int newsId = getIntent().getIntExtra("news_id", 0);
        if (newsId > 0) {

            JsonObject body = new JsonObject();
            body.addProperty("user_id", user.getId());
            body.addProperty("type", "notification");
            //body.addProperty("type", "data");

            Call<String> call = mainApi.shareNews(newsId, body);

            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    finish();
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    finish();
                }
            });


        }
    }
}
