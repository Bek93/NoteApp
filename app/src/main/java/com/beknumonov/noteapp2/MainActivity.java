package com.beknumonov.noteapp2;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;

import com.beknumonov.noteapp2.base.BaseActivity;
import com.beknumonov.noteapp2.databinding.ActivityMainBinding;
import com.beknumonov.noteapp2.fragments.BookFragment;
import com.beknumonov.noteapp2.fragments.NewsFragment;
import com.beknumonov.noteapp2.fragments.NotesFragment;
import com.beknumonov.noteapp2.fragments.ProfileFragment;
import com.beknumonov.noteapp2.model.Note;
import com.beknumonov.noteapp2.model.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.navigation.NavigationBarView;
import com.google.firebase.messaging.FirebaseMessaging;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private ArrayList<Note> noteArrayList;
    private NotesFragment notesFragment;
    private BookFragment bookFragment;
    private NewsFragment newsFragment;
    private ProfileFragment profileFragment;

    @Override
    protected ActivityMainBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityMainBinding.inflate(inflater);
    }

    private String deviceToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        //generateNotes();
        replaceFragment(R.id.newsTab);

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                replaceFragment(item.getItemId());
                return true;
            }
        });
        registerDeviceToken();
        //generateNotes();
    }

    private void registerDeviceToken() {

        FirebaseMessaging.getInstance().getToken().addOnCompleteListener(new OnCompleteListener<String>() {
            @Override
            public void onComplete(@NonNull Task<String> task) {
                if (task.isSuccessful()) {
                    User p_user = (User) preferencesManager.getValue(User.class, "user", null);
                    if (p_user == null)
                        return;
                    String device_token = (String) preferencesManager.getValue(String.class, "device_token", "");
                    deviceToken = task.getResult();
                    if (!device_token.equals(deviceToken)) {
                        // only device token
                        User user = new User();
                        user.setDeviceToken(deviceToken);

                        Call<User> call = mainApi.registerDeviceToken(getBearerToken(), p_user.getId(), user);
                        call.enqueue(new Callback<User>() {
                            @Override
                            public void onResponse(Call<User> call, Response<User> response) {
                                if (response.isSuccessful()) {
                                    preferencesManager.setValue("device_token", deviceToken);
                                }
                            }

                            @Override
                            public void onFailure(Call<User> call, Throwable t) {

                            }
                        });

                    }


                }
            }
        });

    }

    private void replaceFragment(int tabId) {

        if (tabId == R.id.newsTab) {
            if (newsFragment == null)
                newsFragment = new NewsFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, newsFragment).commit();
            setTitle("News");

        } else if (tabId == R.id.noteTab) {

            if (notesFragment == null)
                notesFragment = new NotesFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, notesFragment).commit();
            setTitle("Notes");
        } else if (tabId == R.id.bookTab) {

            if (bookFragment == null)
                bookFragment = new BookFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, bookFragment).commit();
            setTitle("Notes");
        } else if (tabId == R.id.profileTab) {

            if (profileFragment == null)
                profileFragment = new ProfileFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, profileFragment).commit();
            setTitle("Profile");

        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        noteArrayList = databaseHelper.getNotes();

        for (Note note : noteArrayList) {
            Log.d("Note", note.toString());
        }
    }

    void generateNotes() {
        ArrayList<Note> notes = new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Note note = new Note();

            note.setTitle("Title " + (i + 1));
            note.setContent("Content " + (i + 1));
            notes.add(note);
        }
        databaseHelper.addNotes(notes);
    }
}