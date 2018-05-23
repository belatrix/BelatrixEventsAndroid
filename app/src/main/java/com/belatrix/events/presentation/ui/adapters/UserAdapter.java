package com.belatrix.events.presentation.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.domain.model.User;

import java.util.ArrayList;
import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private final List<User> lstUser;
    private final OnItemPressListener mListener;

    public UserAdapter(OnItemPressListener onItemPressListener) {
        lstUser = new ArrayList<>();
        this.mListener = onItemPressListener;
    }

    public void addAll(List<User> lst) {
        lstUser.clear();
        lstUser.addAll(lst);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_search_user, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        User user = lstUser.get(position);
        holder.bind(user);
    }

    @Override
    public int getItemCount() {
        return lstUser.size();
    }

    public interface OnItemPressListener {
        void onItemPressed(User user);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private final OnItemPressListener mListener;

        private TextView tvUserId;
        private TextView tvUserName;

        public ViewHolder(View itemView, OnItemPressListener listener) {
            super(itemView);
            this.mListener = listener;
            tvUserId = itemView.findViewById(R.id.tv_user_id);
            tvUserName = itemView.findViewById(R.id.tv_user_name);
            itemView.setOnClickListener(ViewHolder.this);
        }

        public void bind(User user) {
            tvUserId.setText(user.getEmail());
            tvUserName.setText(user.getFullName());
            itemView.setTag(user);
        }

        @Override
        public void onClick(View v) {
            User user = (User) v.getTag();
            mListener.onItemPressed(user);
        }
    }
}
