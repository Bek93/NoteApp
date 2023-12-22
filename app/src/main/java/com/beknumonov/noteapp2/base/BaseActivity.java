package com.beknumonov.noteapp2.base;

import android.os.Bundle;
import android.view.LayoutInflater;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import com.beknumonov.noteapp2.R;


public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB binding;


    protected abstract VB inflateViewBinding(LayoutInflater inflater);

    protected boolean hasBackButton() {
        return false;
    }

    protected int backButtonDrawable() {
        return 0;
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflateViewBinding(getLayoutInflater());
        setContentView(binding.getRoot());


        Toolbar toolbar = (binding.getRoot()).findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(hasBackButton());

            if (hasBackButton()) {
                if (backButtonDrawable() != 0)
                    getSupportActionBar().setHomeAsUpIndicator(backButtonDrawable());
            }
        }
    }
}
