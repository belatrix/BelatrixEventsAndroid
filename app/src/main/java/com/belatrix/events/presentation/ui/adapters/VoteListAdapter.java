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

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.model.Vote;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class VoteListAdapter extends RecyclerView.Adapter<VoteListAdapter.ViewHolder> {

    private List<Vote> list;
    private RecyclerViewClickListener clickListener;

    public VoteListAdapter(RecyclerViewClickListener clickListener) {
        this(clickListener, new ArrayList<Vote>());
    }

    private VoteListAdapter(RecyclerViewClickListener clickListener, List<Vote> list) {
        this.list = list;
        this.clickListener = clickListener;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_idea_vote, parent, false);
        return new ViewHolder(layoutView, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        final Vote object = list.get(position);
        holder.eventTextView.setText(object.getTitle());
        holder.itemView.setTag(object);
        holder.projectVotesTextView.setText(String.format(Locale.getDefault(), "%d", object.getVotes()));
    }

    @Override
    public int getItemCount() {
        return this.list.size();
    }

    public void updateData(List<Vote> list) {
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
        @BindView(R.id.project_votes)
        TextView projectVotesTextView;

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
