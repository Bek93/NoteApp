package com.beknumonov.noteapp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.beknumonov.noteapp2.base.BaseActivity;
import com.beknumonov.noteapp2.databinding.ActivityRegisterBinding;
import com.beknumonov.noteapp2.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {
    @Override
    protected ActivityRegisterBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityRegisterBinding.inflate(inflater);
    }

    private String deviceToken;

    @Override
    protected boolean hasBackButton() {
        return true;
    }

    @Override
    protected int backButtonDrawable() {
        return R.drawable.ic_back;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setTitle("Registration");


        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    deviceToken = task.getResult();
                    Log.d("Token", deviceToken);
                }
            }
        });


        binding.createAccountBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String firstname = binding.firstNameEditText.getText().toString();
                String lastname = binding.lastNameEditText.getText().toString();
                String email = binding.emailEditText.getText().toString();
                String password = binding.passwordEditText.getText().toString();
                String confirmPassword = binding.confirmPasswordEditText.getText().toString();


                if (firstname.isEmpty()) {
                    return;
                }
                if (lastname.isEmpty()) {
                    return;
                }
                if (email.isEmpty()) {
                    return;
                }

                if (password.isEmpty()) {
                    return;
                }
                if (confirmPassword.isEmpty()) {
                    return;
                }
                if (password.equals(confirmPassword)) {

//                    SharedPreferences sharedPreferences = getSharedPreferences("NoteAppSharedPref", MODE_PRIVATE);
//                    SharedPreferences.Editor editor = sharedPreferences.edit();
//                    editor.putString("firstName", firstname);
//                    editor.putString("lastName", lastname);
//                    editor.putString("email", email);
//                    editor.putString("password", password);
//                    editor.putBoolean("isLoggedIn", true);


                    User user = new User();
                    user.setEmail(email);
                    user.setFirstName(firstname);
                    user.setLastName(lastname);
                    user.setPassword(password);
                    user.setDeviceToken(deviceToken);

                    Call<User> call = mainApi.createUser(user);

                    call.enqueue(new Callback<User>() {
                        @Override
                        public void onResponse(Call<User> call, Response<User> response) {
                            if (response.isSuccessful()) {
                                preferencesManager.setValue("isLoggedIn", true);
                                preferencesManager.setValue("user", response.body());
                                preferencesManager.setValue("email", user.getEmail());
                                preferencesManager.setValue("password", password);
                                preferencesManager.setValue("access_token", response.body().getAccessToken());
                                preferencesManager.setValue("device_token", deviceToken);

                                Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onFailure(Call<User> call, Throwable t) {

                        }
                    });


                } else {

                }

            }
        });

    }
}
