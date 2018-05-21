package com.belatrix.events.presentation.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.utils.media.ImageFactory;
import com.belatrix.events.utils.media.loaders.ImageLoader;

import java.util.ArrayList;
import java.util.List;

public class NewEventAdapter extends RecyclerView.Adapter<NewEventAdapter.EventViewHolder> {

    private final static int MAIN_VIEW = 1;
    private final static int SIDE_VIEW = 2;
    private final OnItemClickListener mOnItemClickListener;
    private List<Event> lstEvent;

    public NewEventAdapter(OnItemClickListener onItemClickListener) {
        this.mOnItemClickListener = onItemClickListener;
        this.lstEvent = new ArrayList<>();
    }

    public void addAll(List<Event> lst) {
        lstEvent.clear();
        lstEvent.addAll(lst);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public EventViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (viewType == MAIN_VIEW) {
            return new MainEventViewHolder(inflater.inflate(R.layout.new_item_upcoming, parent, false), mOnItemClickListener);
        } else {
            return new SideEventViewHolder(inflater.inflate(R.layout.new_item_event, parent, false), mOnItemClickListener);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull EventViewHolder holder, int position) {
        Event event = lstEvent.get(position);
        holder.bind(event);
    }

    @Override
    public int getItemCount() {
        return lstEvent.size();
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? MAIN_VIEW : SIDE_VIEW;
    }

    public interface OnItemClickListener {
        void onItemPressed(ImageView ivEvent, Event event);
    }

    class EventViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnItemClickListener mOnItemClickListener;
        private TextView tvTitle;
        private ImageView ivEvent;
        private ImageView ivMark;

        EventViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tv_title);
            ivEvent = itemView.findViewById(R.id.iv_event);
            ivMark = itemView.findViewById(R.id.iv_mark);
            this.mOnItemClickListener = onItemClickListener;

            itemView.setOnClickListener(EventViewHolder.this);
        }

        void bind(Event event) {
            tvTitle.setText(event.getTitle());
            ivMark.setVisibility(event.isUpcoming() ? View.VISIBLE : View.GONE);
            itemView.setTag(event);
            ImageFactory.getLoader().loadFromUrl(event.getImage(), ivEvent, null, null, ImageLoader.ScaleType.FIT_CENTER);
        }

        @Override
        public void onClick(View v) {
            Event event = (Event) v.getTag();
            mOnItemClickListener.onItemPressed((ImageView) v.findViewById(R.id.iv_event), event);
        }
    }

    class MainEventViewHolder extends EventViewHolder {
        MainEventViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
        }
    }

    class SideEventViewHolder extends EventViewHolder {
        SideEventViewHolder(View itemView, OnItemClickListener onItemClickListener) {
            super(itemView, onItemClickListener);
        }
    }
}
