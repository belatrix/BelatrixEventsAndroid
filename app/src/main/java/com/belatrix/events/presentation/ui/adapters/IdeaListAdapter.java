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

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class IdeaListAdapter extends RecyclerView.Adapter<IdeaListAdapter.ViewHolder> {

    private List<Project> list;
    private RecyclerViewClickListener clickListener;

    public IdeaListAdapter(RecyclerViewClickListener clickListener) {
        this(clickListener, new ArrayList<Project>());
    }

    private IdeaListAdapter(RecyclerViewClickListener clickListener, List<Project> list) {
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_idea, parent, false);
        return new ViewHolder(layoutView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Project object = list.get(position);
        holder.eventTextView.setText(object.getTitle());
        holder.eventDescription.setText(object.getDescription());
        holder.itemView.setTag(object);
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void updateData(List<Project> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    public interface RecyclerViewClickListener {

        void onItemClicked(int position, View view);

    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.event_name)
        TextView eventTextView;
        @BindView(R.id.event_description)
        TextView eventDescription;

        private RecyclerViewClickListener clickListener;

        public ViewHolder(View view, RecyclerViewClickListener clickListener) {
            super(view);
            this.clickListener = clickListener;
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layout_container)
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClicked(getLayoutPosition(), view);
            }
        }
    }
}
