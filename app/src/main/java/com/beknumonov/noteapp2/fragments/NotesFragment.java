package com.beknumonov.noteapp2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.beknumonov.noteapp2.AddNoteActivity;
import com.beknumonov.noteapp2.adapters.NoteListAdapter;
import com.beknumonov.noteapp2.base.BaseFragment;
import com.beknumonov.noteapp2.base.LoadingBarDialog;
import com.beknumonov.noteapp2.base.RequestCallback;
import com.beknumonov.noteapp2.databinding.FragmentNotesBinding;
import com.beknumonov.noteapp2.databinding.FragmentProfileBinding;
import com.beknumonov.noteapp2.model.Note;
import com.beknumonov.noteapp2.model.User;
import com.beknumonov.noteapp2.remote.MainApi;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class NotesFragment extends BaseFragment<FragmentNotesBinding> {

    @Override
    protected FragmentNotesBinding inflateViewBinding(LayoutInflater inflater, ViewGroup parent, boolean toAttachRoot) {
        return FragmentNotesBinding.inflate(inflater, parent, false);
    }

    private NoteListAdapter noteListAdapter;
    private ArrayList<Note> noteArrayList = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        noteListAdapter = new NoteListAdapter(noteArrayList);


    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.notesRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false));
        binding.notesRecyclerView.setAdapter(noteListAdapter);

        binding.addNoteBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), AddNoteActivity.class);
                startActivity(intent);
            }
        });


    }


    private void loadNotes() {

        User user = (User) parent.preferencesManager.getValue(User.class, "user", null);
        if (user != null) {
            Log.d("User", user.toString());
        }


        Call<ArrayList<Note>> call = parent.mainApi.getNotes();

        //call.execute();

        parent.showLoading();
        /*call.enqueue(new Callback<ArrayList<Note>>() {
            @Override
            public void onResponse(Call<ArrayList<Note>> call, Response<ArrayList<Note>> response) {
                parent.hideLoading();
                if (response.isSuccessful()) {
                    noteArrayList.clear();
                    ArrayList<Note> notes = response.body();
                    noteArrayList.addAll(notes);
                    noteListAdapter.notifyDataSetChanged();
                } else {
                    onFailure(call, new Throwable("Failed"));
                }
            }

            @Override
            public void onFailure(Call<ArrayList<Note>> call, Throwable t) {
                parent.hideLoading();
                Log.e("Error", t.getLocalizedMessage());
            }
        });*/

        call.enqueue(new RequestCallback<ArrayList<Note>>() {
            @Override
            protected void onResponseSuccess(Call<ArrayList<Note>> call, Response<ArrayList<Note>> response) {
                parent.hideLoading();
                noteArrayList.clear();
                ArrayList<Note> notes = response.body();
                noteArrayList.addAll(notes);
                noteListAdapter.notifyDataSetChanged();
            }

            @Override
            protected void onResponseFail(Call<ArrayList<Note>> call, Throwable t) {
                parent.hideLoading();
                Log.e("Error", t.getLocalizedMessage());
            }
        });


    }

    @Override
    public void onStart() {
        super.onStart();
        //noteArrayList.clear();
        //noteArrayList.addAll(parent.databaseHelper.getNotes());
        loadNotes();
    }

    @Override
    public void onResume() {
        super.onResume();
        noteListAdapter.notifyDataSetChanged();
    }
}
