package com.belatrixsf.events.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.ApplicationComponent;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;

import butterknife.BindView;

/**
 * created by dvelasquez
 */
public class HomeFeaturedItemFragment extends BelatrixBaseFragment  {

    private Event event;
    @BindView(R.id.name_textview)
    TextView nameTextView;

    public HomeFeaturedItemFragment() {
    }

    public static HomeFeaturedItemFragment newInstance(Event event) {
        HomeFeaturedItemFragment fragment = new HomeFeaturedItemFragment();
        fragment.event = event;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initDependencies(ApplicationComponent applicationComponent) {

    }

    @Override
    protected void initViews() {
        nameTextView.setText(event.getName());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_featured_item, container, false);
    }
}
