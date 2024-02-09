package com.beknumonov.noteapp2;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;

import androidx.annotation.Nullable;

import com.beknumonov.noteapp2.base.BaseActivity;
import com.beknumonov.noteapp2.databinding.ActivityNoteDetailsBinding;
import com.beknumonov.noteapp2.model.Note;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NoteDetailsActivity extends BaseActivity<ActivityNoteDetailsBinding> {
    private Note note;


    @Override
    protected ActivityNoteDetailsBinding inflateViewBinding(LayoutInflater inflater) {
        return ActivityNoteDetailsBinding.inflate(inflater);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        // getting from intent ( from other activity)
        note = (Note) getIntent().getSerializableExtra("note");
        setTitle("Note Details");

        // checking note and set values
        if (note != null) {
            binding.titleTv.setText(note.getTitle());
            binding.contentTv.setText(note.getContent());
        } else {
            //close if it is null
            finish();
        }


        binding.editBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(NoteDetailsActivity.this, AddNoteActivity.class);
                intent.putExtra("note", note);
                startActivity(intent);
                finish();
            }
        });

        binding.removeBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //databaseHelper.deleteNote(note);
                //finish();

                showLoading();
                Call<Void> call = mainApi.deleteNote( note.getId());
                call.enqueue(new Callback<Void>() {
                    @Override
                    public void onResponse(Call<Void> call, Response<Void> response) {
                        hideLoading();
                        finish();
                    }

                    @Override
                    public void onFailure(Call<Void> call, Throwable t) {
                        hideLoading();
                    }
                });
            }
        });
    }

    @Override
    protected boolean hasBackButton() {
        return true;
    }
}
