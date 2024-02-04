package com.beknumonov.noteapp2.adapters;

import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.beknumonov.noteapp2.AddNoteActivity;
import com.beknumonov.noteapp2.NoteDetailsActivity;
import com.beknumonov.noteapp2.base.BaseRecyclerAdapter;
import com.beknumonov.noteapp2.base.BaseViewHolder;
import com.beknumonov.noteapp2.databinding.ItemNoteBinding;
import com.beknumonov.noteapp2.model.Note;

import java.util.ArrayList;

public class NoteListAdapter extends BaseRecyclerAdapter {

    private ArrayList<Note> noteArrayList;

    public NoteListAdapter(ArrayList<Note> noteArrayList) {
        this.noteArrayList = noteArrayList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemNoteBinding binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new NoteViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Note note = noteArrayList.get(holder.getAdapterPosition());

                Intent intent = new Intent(holder.itemView.getContext(), NoteDetailsActivity.class);
                //intent.putExtra("note_id", note.getId());
                intent.putExtra("note", note);
                holder.itemView.getContext().startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return noteArrayList.size();
    }

    public class NoteViewHolder extends BaseViewHolder<ItemNoteBinding> {

        public NoteViewHolder(ItemNoteBinding binding) {
            super(binding);
        }

        @Override
        public void onBind(int position) {
            Note note = noteArrayList.get(position);
            binding.noteTitleTv.setText(String.valueOf(note.getId()) + ". " + note.getTitle());
            binding.noteContentTv.setText(note.getContent());
        }

    }
}
