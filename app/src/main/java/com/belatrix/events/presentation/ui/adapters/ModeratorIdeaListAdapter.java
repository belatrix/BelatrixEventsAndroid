package com.belatrix.events.presentation.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.domain.model.Project;

import java.util.ArrayList;
import java.util.List;

public class ModeratorIdeaListAdapter extends RecyclerView.Adapter<ModeratorIdeaListAdapter.ViewHolder> {

    private final List<Project> lstProject;

    private final OnItemPressListener mOnItemPressListener;

    public ModeratorIdeaListAdapter(OnItemPressListener onItemPressListener) {
        this.lstProject = new ArrayList<>();
        this.mOnItemPressListener = onItemPressListener;
    }

    public void addAll(List<Project> lst) {
        lstProject.clear();
        lstProject.addAll(lst);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item__moderator_idea, parent, false), mOnItemPressListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Project project = lstProject.get(position);
        holder.bind(project);
    }

    @Override
    public int getItemCount() {
        return lstProject.size();
    }

    public interface OnItemPressListener {
        void onItemPressed(Project project);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnItemPressListener mOnItemPressListener;

        private TextView tvEvent;

        public ViewHolder(View itemView, OnItemPressListener onItemPressListener) {
            super(itemView);
            mOnItemPressListener = onItemPressListener;
            tvEvent = itemView.findViewById(R.id.tv_idea_title);
            itemView.setOnClickListener(ViewHolder.this);
        }

        public void bind(Project project) {
            tvEvent.setText(project.getTitle());
            tvEvent.setTag(project);
        }

        @Override
        public void onClick(View v) {
            Project project = (Project) v.getTag();
            mOnItemPressListener.onItemPressed(project);
        }
    }
}
