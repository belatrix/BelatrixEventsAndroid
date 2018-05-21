package com.belatrix.events.presentation.ui.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.domain.model.Meeting;

import java.util.ArrayList;
import java.util.List;

public class MeetingListAdapter extends RecyclerView.Adapter<MeetingListAdapter.ViewHolder> {

    private final List<Meeting> lstMeetings;
    private final OnItemPressedListener mListener;

    public MeetingListAdapter(OnItemPressedListener listener) {
        lstMeetings = new ArrayList<>();
        mListener = listener;
    }

    public void addAll(List<Meeting> auxLstEvent) {
        lstMeetings.clear();
        lstMeetings.addAll(auxLstEvent);
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public MeetingListAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_meeting, parent, false), mListener);
    }

    @Override
    public void onBindViewHolder(@NonNull MeetingListAdapter.ViewHolder holder, int position) {
        Meeting meeting = lstMeetings.get(position);
        holder.bind(meeting);
    }

    @Override
    public int getItemCount() {
        return lstMeetings.size();
    }

    public interface OnItemPressedListener {
        void onItemPressed(Meeting meeting);
    }

    static class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        private final OnItemPressedListener mListener;
        TextView tvEventName;

        public ViewHolder(View itemView, OnItemPressedListener listener) {
            super(itemView);
            this.mListener = listener;
            tvEventName = itemView.findViewById(R.id.tv_event_title);
        }

        public void bind(Meeting meeting) {
            tvEventName.setText(meeting.getName());
            tvEventName.setTag(meeting);
            tvEventName.setOnClickListener(ViewHolder.this);
        }

        @Override
        public void onClick(View v) {
            Meeting meeting = (Meeting) v.getTag();
            mListener.onItemPressed(meeting);
        }
    }

}
