package com.beknumonov.noteapp2.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.beknumonov.noteapp2.base.BaseFragment;
import com.beknumonov.noteapp2.databinding.FragmentProfileBinding;

public class ProfileFragment extends BaseFragment<FragmentProfileBinding> {
    @Override
    protected FragmentProfileBinding inflateViewBinding(LayoutInflater inflater, ViewGroup parent, boolean toAttachRoot) {
        return FragmentProfileBinding.inflate(inflater, parent, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        String firstName = (String) parent.preferencesManager.getValue(String.class, "firstName", "");
        String lastName = (String) parent.preferencesManager.getValue(String.class, "lastName", "");
        String fullName = lastName + " " + firstName;
        binding.fullName.setText(fullName);
    }
}
