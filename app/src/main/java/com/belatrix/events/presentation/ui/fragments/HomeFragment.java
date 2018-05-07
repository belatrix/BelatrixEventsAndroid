//package com.belatrix.events.presentation.ui.fragments;
//
//import android.graphics.drawable.Drawable;
//import android.os.Bundle;
//import android.support.v4.content.ContextCompat;
//import android.support.v4.widget.NestedScrollView;
//import android.support.v4.widget.SwipeRefreshLayout;
//import android.view.LayoutInflater;
//import android.view.View;
//import android.view.ViewGroup;
//import android.widget.ImageView;
//import android.widget.TextView;
//
//import com.belatrix.events.R;
//import com.belatrix.events.di.component.UIComponent;
//import com.belatrix.events.domain.model.Event;
//import com.belatrix.events.presentation.presenters.HomeFragmentPresenter;
//import com.belatrix.events.presentation.ui.activities.EventDetailActivity;
//import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
//import com.belatrix.events.utils.Constants;
//import com.belatrix.events.utils.media.ImageFactory;
//import com.belatrix.events.utils.media.loaders.ImageLoader;
//
//import javax.inject.Inject;
//
//import butterknife.BindDrawable;
//import butterknife.BindString;
//import butterknife.BindView;
//import butterknife.OnClick;
//
///**
// * created by dvelasquez
// */
//public class HomeFragment extends BelatrixBaseFragment implements HomeFragmentPresenter.View {
//
//
//    @BindString(R.string.event_title_near)
//    String eventTitleNear;
//    @BindString(R.string.event_title_past)
//    String eventTitlePast;
//    @BindView(R.id.image_home)
//    ImageView homeImageView;
//    @BindDrawable(R.drawable.event_placeholder)
//    Drawable placeHolderDrawable;
//    @BindView(R.id.swipe_refresh_layout)
//    SwipeRefreshLayout swipeRefreshLayout;
//    @BindView(R.id.title_home)
//    TextView titleTextView;
//    @BindView(R.id.scrollview)
//    NestedScrollView scrollview;
//
//    @Inject
//    HomeFragmentPresenter presenter;
//
//    public HomeFragment() {
//    }
//
//    public static HomeFragment newInstance() {
//        HomeFragment fragment = new HomeFragment();
//        return fragment;
//    }
//
//    @Override
//    public void onCreate(Bundle savedInstanceState) {
//        super.onCreate(savedInstanceState);
//    }
//
//    @Override
//    protected void initDependencies(UIComponent uiComponent) {
//        uiComponent.inject(this);
//        presenter.setView(this);
//    }
//
//    @OnClick(R.id.image_home)
//    public void onClickHomeEvent() {
//        Event event = presenter.getEvent();
//        if (event != null) {
//           EventDetailActivity.startActivity(getActivity(), presenter.getEvent(),homeImageView);
//        }
//    }
//
//    @Override
//    protected void initViews() {
//        loadViews();
//        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                swipeRefreshLayout.setRefreshing(false);
//                loadViews();
//            }
//        });
//    }
//
//    private void loadViews() {
//
//        EventListSummaryFragment fragment1 = (EventListSummaryFragment)getChildFragmentManager().findFragmentById(R.id.frame_events_near);
//        EventListSummaryFragment fragment2 = (EventListSummaryFragment)getChildFragmentManager().findFragmentById(R.id.frame_events_past);
//        if (fragment1 != null){
//            fragment1.cancelRequest();
//        }
//        if (fragment2 != null){
//            fragment2.cancelRequest();
//        }
//        replaceChildFragment(EventListSummaryFragment.newInstance(Constants.EVENT_TYPE_UPCOMING, eventTitleNear), R.id.frame_events_near);
//        replaceChildFragment(EventListSummaryFragment.newInstance(Constants.EVENT_TYPE_PAST, eventTitlePast), R.id.frame_events_past);
//        scrollview.smoothScrollTo(0,0);
//        presenter.actionLoadHomeEvent();
//    }
//
//    @Override
//    public void showHomeEvent(Event event) {
//        presenter.setEvent(event);
//        ImageFactory.getLoader().loadFromUrl(event.getImage(), homeImageView, null, null, ImageLoader.ScaleType.FIT_CENTER);
//        titleTextView.setText(event.getTitle());
//        titleTextView.setBackground(ContextCompat.getDrawable(getActivity(),R.drawable.background_event_title));
//    }
//
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container,
//                             Bundle savedInstanceState) {
//        return inflater.inflate(R.layout.fragment_home, container, false);
//    }
//
//    @Override
//    public void onViewCreated(View view, Bundle savedInstanceState) {
//        super.onViewCreated(view, savedInstanceState);
//        if (savedInstanceState != null){
//            presenter.setEvent((Event) savedInstanceState.getParcelable(Constants.EVENT_KEY));
//        }
//    }
//
//    @Override
//    public void onSaveInstanceState(Bundle outState) {
//        outState.putParcelable(Constants.EVENT_KEY, presenter.getEvent());
//        super.onSaveInstanceState(outState);
//    }
//
//    @Override
//    public void onDestroyView() {
//        super.onDestroyView();
//        presenter.cancelRequests();
//    }
//}
