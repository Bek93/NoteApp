package com.beknumonov.noteapp2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.beknumonov.noteapp2.AddNoteActivity;
import com.beknumonov.noteapp2.adapters.NoteListAdapter;
import com.beknumonov.noteapp2.base.BaseFragment;
import com.beknumonov.noteapp2.databinding.FragmentNotesBinding;
import com.beknumonov.noteapp2.databinding.FragmentProfileBinding;
import com.beknumonov.noteapp2.model.Note;

import java.util.ArrayList;

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

    @Override
    public void onStart() {
        super.onStart();
        noteArrayList.clear();
        noteArrayList.addAll(parent.databaseHelper.getNotes());
    }

    @Override
    public void onResume() {
        super.onResume();
        noteListAdapter.notifyDataSetChanged();
    }
}
