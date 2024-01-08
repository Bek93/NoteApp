package com.beknumonov.noteapp2.base;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.viewbinding.ViewBinding;

import com.beknumonov.noteapp2.R;
import com.beknumonov.noteapp2.db.DatabaseHelper;
import com.beknumonov.noteapp2.util.PreferencesManager;


public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB binding;


    protected abstract VB inflateViewBinding(LayoutInflater inflater);

    protected boolean hasBackButton() {
        return false;
    }

    protected int backButtonDrawable() {
        return 0;
    }


    public PreferencesManager preferencesManager;
    public DatabaseHelper databaseHelper;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflateViewBinding(getLayoutInflater());
        setContentView(binding.getRoot());

        preferencesManager = PreferencesManager.getInstance(getApplicationContext());
        databaseHelper = new DatabaseHelper(this);
        Toolbar toolbar = (binding.getRoot()).findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);


        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(hasBackButton());

            if (hasBackButton()) {
                if (backButtonDrawable() != 0)
                    getSupportActionBar().setHomeAsUpIndicator(backButtonDrawable());
            }

            toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    finish();
                }
            });
        }


    }

//
//    @Override
//    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
//        if (item.getItemId() == android.R.id.home) {
//            finish();
//        }
//
//        return super.onOptionsItemSelected(item);
//    }
}
