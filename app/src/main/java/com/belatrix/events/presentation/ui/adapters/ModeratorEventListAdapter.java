package com.belatrix.events.presentation.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.domain.model.Event;

import java.util.ArrayList;
import java.util.List;

public class ModeratorEventListAdapter extends RecyclerView.Adapter<ModeratorEventListAdapter.ViewHolder> {

    private final List<Event> lstEvent;

    private final OnItemPressListener mOnItemPressListener;

    public ModeratorEventListAdapter(OnItemPressListener onItemPressListener) {
        this.lstEvent = new ArrayList<>();
        this.mOnItemPressListener = onItemPressListener;
    }

    public void addAll(List<Event> lst) {
        lstEvent.clear();
        lstEvent.addAll(lst);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item__moderator_event, parent, false), mOnItemPressListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Event event = lstEvent.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return lstEvent.size();
    }

    public interface OnItemPressListener {
        void onItemPressed(Event event);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnItemPressListener mOnItemPressListener;

        private TextView tvEvent;

        public ViewHolder(View itemView, OnItemPressListener onItemPressListener) {
            super(itemView);
            mOnItemPressListener = onItemPressListener;
            tvEvent = itemView.findViewById(R.id.tv_event_title);
            itemView.setOnClickListener(ViewHolder.this);
        }

        public void bind(Event event) {
            tvEvent.setText(event.getTitle());
            tvEvent.setTag(event);
        }

        @Override
        public void onClick(View v) {
            Event event = (Event) v.getTag();
            mOnItemPressListener.onItemPressed(event);
        }
    }
}
