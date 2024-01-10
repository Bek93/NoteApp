package com.beknumonov.noteapp2;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.beknumonov.noteapp2.base.BaseActivity;
import com.beknumonov.noteapp2.databinding.ActivityAddNewNoteBinding;
import com.beknumonov.noteapp2.model.Note;

public class AddNoteActivity extends BaseActivity<ActivityAddNewNoteBinding> {

    private Note note;

    @Override
    protected ActivityAddNewNoteBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityAddNewNoteBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //int note_id = getIntent().getIntExtra("note_id", 0);
        note = (Note) getIntent().getSerializableExtra("note");
        if (note != null) {

            binding.createNote.setText("Update Note");
            binding.titleEditText.setText(note.getTitle());
            binding.contentEditText.setText(note.getContent());
            setTitle("Update Note");
        } else {
            setTitle("Create Note");
        }


        binding.createNote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String title = binding.titleEditText.getText().toString();
                String content = binding.contentEditText.getText().toString();
                if (note == null) {
                    Note note = new Note(title, content);
                    databaseHelper.addNote(note, false);
                } else {
                    note.setTitle(title);
                    note.setContent(content);
                    databaseHelper.updateNote(note);
                }
                finish();

            }
        });
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }
}
