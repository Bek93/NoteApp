package com.beknumonov.noteapp2.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.beknumonov.noteapp2.AddBookActivity;
import com.beknumonov.noteapp2.base.BaseFragment;
import com.beknumonov.noteapp2.databinding.FragmentBookBinding;

public class BookFragment extends BaseFragment<FragmentBookBinding> {
    @Override
    protected FragmentBookBinding inflateViewBinding(LayoutInflater inflater, ViewGroup parent, boolean toAttachRoot) {
        return FragmentBookBinding.inflate(inflater, parent, toAttachRoot);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.createBook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(requireContext(), AddBookActivity.class);
                startActivity(intent);
            }
        });
    }
}
