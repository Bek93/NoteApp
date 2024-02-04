package com.beknumonov.noteapp2.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;

import com.beknumonov.noteapp2.base.BaseRecyclerAdapter;
import com.beknumonov.noteapp2.base.BaseViewHolder;
import com.beknumonov.noteapp2.databinding.ItemUserBinding;
import com.beknumonov.noteapp2.model.User;

import java.util.ArrayList;

public class UserListAdapter extends BaseRecyclerAdapter {

    private ArrayList<User> userArrayList;

    private UserSelectListener userSelectListener;

    public void setUserSelectListener(UserSelectListener userSelectListener) {
        this.userSelectListener = userSelectListener;
    }

    public UserListAdapter(ArrayList<User> userArrayList) {
        this.userArrayList = userArrayList;
    }

    @NonNull
    @Override
    public BaseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemUserBinding itemUserBinding = ItemUserBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);

        return new UserViewHolder(itemUserBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull BaseViewHolder holder, int position) {
        super.onBindViewHolder(holder, position);
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (userSelectListener != null)
                    userSelectListener.onSelected(userArrayList.get(holder.getAdapterPosition()));
            }
        });
    }

    @Override
    public int getItemCount() {
        return userArrayList.size();
    }

    public class UserViewHolder extends BaseViewHolder {
        ItemUserBinding binding;

        public UserViewHolder(@NonNull ItemUserBinding binding) {
            super(binding);
            this.binding = binding;
        }

        @Override
        public void onBind(int position) {
            User user = userArrayList.get(position);

            binding.userFullName.setText(user.getEmail());
        }
    }


    public interface UserSelectListener {
        void onSelected(User user);
    }
}
