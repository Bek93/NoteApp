package com.beknumonov.noteapp2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.beknumonov.noteapp2.base.BaseActivity;
import com.beknumonov.noteapp2.databinding.ActivityAddNewNoteBinding;
import com.beknumonov.noteapp2.model.Note;

public class AddNoteActivity extends BaseActivity<ActivityAddNewNoteBinding> {
    @Override
    protected ActivityAddNewNoteBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityAddNewNoteBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding.createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.titleEditText.getText().toString();
                String content = binding.contentEditText.getText().toString();
                Note note = new Note(title, content);

                databaseHelper.addNote(note, false);

                finish();

            }
        });
    }
}
