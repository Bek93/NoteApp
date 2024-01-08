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
import com.beknumonov.noteapp2.fragments.NotesFragment;
import com.beknumonov.noteapp2.fragments.ProfileFragment;
import com.beknumonov.noteapp2.model.Note;
import com.google.android.material.navigation.NavigationBarView;

import java.util.ArrayList;

public class MainActivity extends BaseActivity<ActivityMainBinding> {

    private ArrayList<Note> noteArrayList;
    private NotesFragment notesFragment;
    private ProfileFragment profileFragment;

    @Override
    protected ActivityMainBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityMainBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


//        binding.logoutBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
////                SharedPreferences sharedPreferences = getSharedPreferences("NoteAppSharedPref", MODE_PRIVATE);
////                SharedPreferences.Editor editor = sharedPreferences.edit();
////                editor.putBoolean("isLoggedIn", false);
////                editor.apply();
//
//                preferencesManager.setValue("isLoggedIn", false);
//                Intent intent = new Intent(MainActivity.this, SplashActivity.class);
//                startActivity(intent);
//            }
//        });

//        binding.createNoteBtn.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(MainActivity.this, AddNoteActivity.class);
//                startActivity(intent);
//            }
//        });

        //generateNotes();
        replaceFragment(R.id.noteTab);

        binding.bottomNavigationView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                replaceFragment(item.getItemId());
                return true;
            }
        });

        //generateNotes();
    }

    private void replaceFragment(int tabId) {

        if (tabId == R.id.noteTab) {

            if (notesFragment == null)
                notesFragment = new NotesFragment();

            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, notesFragment).commit();
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