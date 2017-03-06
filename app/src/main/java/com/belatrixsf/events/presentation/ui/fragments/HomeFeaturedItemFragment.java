package com.belatrixsf.events.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.UIComponent;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.presentation.ui.activities.EventDetailActivity;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrixsf.events.utils.media.ImageFactory;
import com.belatrixsf.events.utils.media.loaders.ImageLoader;

import butterknife.BindView;
import butterknife.OnClick;
import timber.log.Timber;

/**
 * created by dvelasquez
 */
public class HomeFeaturedItemFragment extends BelatrixBaseFragment  {

    private Event event;
    @BindView(R.id.name_textview)
    TextView nameTextView;
    @BindView(R.id.image_event_featured)
    ImageView eventFeaturedImageView;

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
    protected void initDependencies(UIComponent uiComponent) {

    }

    @Override
    protected void initViews() {
        nameTextView.setText(event.getName());
        ImageFactory.getLoader().loadFromUrl(event.getImage(),
                eventFeaturedImageView,
                null,
                getResources().getDrawable(R.drawable.event_placeholder),
                ImageLoader.ScaleType.CENTER_CROP
        );
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_featured_item, container, false);
    }


    @OnClick(R.id.item_layout)
    public void onClickItem(){
        startActivity(EventDetailActivity.makeIntent(getActivity(),event));
    }
}
