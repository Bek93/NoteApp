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
import com.beknumonov.noteapp2.remote.MainApi;
import com.beknumonov.noteapp2.util.PreferencesManager;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public abstract class BaseActivity<VB extends ViewBinding> extends AppCompatActivity {

    protected VB binding;


    protected abstract VB inflateViewBinding(LayoutInflater inflater);

    protected boolean hasBackButton() {
        return false;
    }

    protected int backButtonDrawable() {
        return 0;
    }


    private LoadingBarDialog dialog;
    public PreferencesManager preferencesManager;
    public DatabaseHelper databaseHelper;

    public MainApi mainApi;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = inflateViewBinding(getLayoutInflater());
        setContentView(binding.getRoot());
        dialog = new LoadingBarDialog(this);
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


        HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient client = new OkHttpClient().newBuilder().addInterceptor(logging).build();


        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://api.note-app.beknumonov.com")
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .build();


        mainApi = retrofit.create(MainApi.class);


    }

    public void showLoading() {
        if (!dialog.isShowing())
            dialog.show();
    }

    public void hideLoading() {
        dialog.hide();
    }

    public String getBearerToken() {
        String access_token = (String) preferencesManager.getValue(String.class, "access_token", "");
        String bearerToken = "Bearer " + access_token;
        return bearerToken;
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
