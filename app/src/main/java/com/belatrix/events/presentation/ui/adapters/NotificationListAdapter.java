package com.belatrix.events.presentation.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.domain.model.Notification;
import com.belatrix.events.utils.DateUtils;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * Created by raulrashuaman on 4/12/17.
 */

public class NotificationListAdapter extends RecyclerView.Adapter<NotificationListAdapter.ViewHolder> {

    private List<Notification> list;

    public NotificationListAdapter() {
        this(new ArrayList<Notification>());
    }

    public NotificationListAdapter(List<Notification> list) {
        this.list = list;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_notification, parent, false);
        return new ViewHolder(layoutView);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Notification object = this.list.get(position);
        String notificationDate = object.getDateTime();
        holder.dateTextView.setText(DateUtils.formatDate(notificationDate,
                DateUtils.DATE_FORMAT_5,
                DateUtils.DATE_FORMAT_4));
        holder.messageTextView.setText(object.getText());
        holder.itemView.setTag(object);
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void updateData(List<Notification> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.notification_date) public TextView dateTextView;
        @BindView(R.id.notification_message) public TextView messageTextView;

        public ViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }
    }
}
