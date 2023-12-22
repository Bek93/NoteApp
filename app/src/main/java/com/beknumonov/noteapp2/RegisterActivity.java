package com.beknumonov.noteapp2;

import android.os.Bundle;
import android.view.LayoutInflater;

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


    }
}
