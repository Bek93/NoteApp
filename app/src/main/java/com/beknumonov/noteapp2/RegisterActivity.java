package com.beknumonov.noteapp2;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.beknumonov.noteapp2.base.BaseActivity;
import com.beknumonov.noteapp2.databinding.ActivityRegisterBinding;

public class RegisterActivity extends BaseActivity<ActivityRegisterBinding> {
    @Override
    protected ActivityRegisterBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityRegisterBinding.inflate(inflater);
    }

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

                    SharedPreferences sharedPreferences = getSharedPreferences("NoteAppSharedPref", MODE_PRIVATE);

                    SharedPreferences.Editor editor = sharedPreferences.edit();
                    editor.putString("firstName", firstname);
                    editor.putString("lastName", lastname);
                    editor.putString("email", email);
                    editor.putString("password", password);
                    editor.putBoolean("isLoggedIn", true);

                    if (editor.commit()) {
                        Intent intent = new Intent(RegisterActivity.this, MainActivity.class);
                        startActivity(intent);
                    }
                } else {

                }

            }
        });

    }
}
