/* The MIT License (MIT)
* Copyright (c) 2016 BELATRIX
* Permission is hereby granted, free of charge, to any person obtaining a copy
* of this software and associated documentation files (the "Software"), to deal
* in the Software without restriction, including without limitation the rights
* to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
* copies of the Software, and to permit persons to whom the Software is
* furnished to do so, subject to the following conditions:

* The above copyright notice and this permission notice shall be included in all
* copies or substantial portions of the Software.

* THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
* IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
* FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
* AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
* LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
* OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
* SOFTWARE.
*/
package com.belatrix.events.presentation.ui.adapters;

import android.graphics.drawable.Drawable;
import android.support.annotation.LayoutRes;
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

import butterknife.BindDrawable;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class EventListAdapter extends RecyclerView.Adapter<EventListAdapter.ViewHolder> {

    private List<Event> list;
    private RecyclerViewClickListener clickListener;
    @LayoutRes
    private int eventLayout;

    public EventListAdapter(RecyclerViewClickListener clickListener,@LayoutRes int eventLayout) {
        this(clickListener, new ArrayList<Event>(), eventLayout);
    }

    public EventListAdapter(RecyclerViewClickListener clickListener, List<Event> list, @LayoutRes int eventLayout) {
        this.list = list;
        this.eventLayout = eventLayout;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(eventLayout, parent, false);
        return new ViewHolder(layoutView, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Event object = list.get(position);
        String fullName = object.getTitle();
        holder.eventTextView.setText(fullName);
        holder.itemView.setTag(object);
        ImageFactory.getLoader().loadFromUrl(object.getImage(),
                holder.eventImageView,
                null,
                null,
                ImageLoader.ScaleType.FIT_CENTER
        );
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void updateData(List<Event> list) {
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }

    public void clear() {
        list.clear();
        notifyDataSetChanged();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.event_title) public TextView eventTextView;
        @BindView(R.id.event_picture) public ImageView eventImageView;
        @BindDrawable(R.drawable.event_placeholder)
        Drawable eventPlaceHolderDrawable;

        private RecyclerViewClickListener clickListener;

        public ViewHolder(View view, RecyclerViewClickListener clickListener) {
            super(view);
            this.clickListener = clickListener;
            ButterKnife.bind(this, view);
        }

        @OnClick(R.id.layout_container)
        public void onClick(View view) {
            if (clickListener != null) {
                clickListener.onItemClicked((Event) itemView.getTag(), eventImageView);
            }
        }

//          Disable onMoreClick
//        @OnClick({R.id.event_title})
        public void onMoreClick(View view){
            if (clickListener != null) {
                clickListener.onItemMoreClicked(itemView);
            }
        }
    }

    public interface RecyclerViewClickListener {

        void onItemClicked(Event event, ImageView view);
        void onItemMoreClicked(View view);
    }

}
