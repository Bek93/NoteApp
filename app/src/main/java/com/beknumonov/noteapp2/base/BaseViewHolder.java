package com.beknumonov.noteapp2.base;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewbinding.ViewBinding;

public abstract class BaseViewHolder<VB extends ViewBinding> extends RecyclerView.ViewHolder {

    protected VB binding;

    public BaseViewHolder(VB binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    public abstract void onBind(int position);
}
