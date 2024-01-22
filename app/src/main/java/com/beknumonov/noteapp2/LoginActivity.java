package com.beknumonov.noteapp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.beknumonov.noteapp2.base.BaseActivity;
import com.beknumonov.noteapp2.databinding.ActivityLoginBinding;
import com.beknumonov.noteapp2.model.User;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class LoginActivity extends BaseActivity<ActivityLoginBinding> {
    @Override
    protected ActivityLoginBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityLoginBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding.loginBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String email = binding.emailEditText.getText().toString();
                String password = binding.passwordEditText.getText().toString();
                //SharedPreferences sharedPreferences = getSharedPreferences("NoteAppSharedPref", MODE_PRIVATE);
                /*String emailFromSh = (String) preferencesManager.getValue(String.class, "email", "");
                String passwordFromSh = (String)
                        preferencesManager.getValue(String.class, "password", "");

                if (email.equals(emailFromSh) && password.equals(passwordFromSh)) {
                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                } else {
                    binding.emailEditText.setError("Email is not founded!");
                    binding.passwordEditText.setError("Password is not founded!");
                }*/


                // Login with Internet

                User user = new User(email, password);

                Log.d("LoginActivity", new Gson().toJson(user));


                Call<User> call = mainApi.login(user);

                showLoading();
                // run in background
                call.enqueue(new Callback<User>() {
                    @Override
                    public void onResponse(Call<User> call, Response<User> response) {
                        hideLoading();
                        if (response.isSuccessful()) {
                            User user = response.body();
                            if (user != null) {
                                preferencesManager.setValue("isLoggedIn", true);
                                preferencesManager.setValue("access_token", user.getAccess());
//                                preferencesManager.setValue("email", user.getEmail());
//                                preferencesManager.setValue("first_name", user.getFirstName());
//                                preferencesManager.setValue("last_name", user.getLastName());

                                preferencesManager.setValue("user", user);
                                Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                                startActivity(intent);

                            }

                        }
                    }

                    @Override
                    public void onFailure(Call<User> call, Throwable t) {
                        hideLoading();
                        Log.d("onFailure", t.getMessage());
                    }
                });


            }
        });

        binding.moveToRegisterPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                startActivity(intent);
            }
        });
    }
}
