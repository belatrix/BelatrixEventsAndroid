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
package com.belatrixsf.events.presentation.ui.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrixsf.events.R;
import com.belatrixsf.events.domain.model.Project;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class ProjectListAdapter extends RecyclerView.Adapter<ProjectListAdapter.ViewHolder> {

    static final int FIRST_POSITION = 1;
    static final int SECOND_POSITION = 2;
    static final int THIRD_POSITION = 3;

    private List<Project> list;
    private RecyclerViewClickListener clickListener;

    public ProjectListAdapter(RecyclerViewClickListener clickListener) {
        this(clickListener, new ArrayList<Project>());
    }

    public ProjectListAdapter(RecyclerViewClickListener clickListener, List<Project> list) {
        this.list = list;
        this.clickListener = clickListener;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View layoutView = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_project, parent, false);
        return new ViewHolder(layoutView, clickListener);
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        final Project object = list.get(position);
        String fullName = object.getText();
        holder.eventTextView.setText(fullName);
        holder.itemView.setTag(object);
        holder.projectVotesTextView.setText("" + object.getVotes());
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

    static class ViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.event_name) public TextView eventTextView;
        @BindView(R.id.project_votes) public TextView projectVotesTextView;
        @BindView(R.id.view_votes) public View votesView;

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

    public interface RecyclerViewClickListener {

        void onItemClicked(int position, View view);

    }

}
