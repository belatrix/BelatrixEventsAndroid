package com.belatrixsf.events.presentation.ui.fragments;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.ApplicationComponent;
import com.belatrixsf.events.di.module.HomeFeaturedModule;
import com.belatrixsf.events.domain.model.Event;
import com.belatrixsf.events.presentation.presenters.HomeFeaturedPresenter;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;
import timber.log.Timber;

/**
 * created by dvelasquez
 */
public class HomeFeaturedFragment extends BelatrixBaseFragment implements HomeFeaturedPresenter.View {

    @Inject
    HomeFeaturedPresenter presenter;
    @BindView(R.id.view_pager)
    ViewPager viewPager;

    public HomeFeaturedFragment() {
    }

    public static HomeFeaturedFragment newInstance() {
        HomeFeaturedFragment fragment = new HomeFeaturedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void initDependencies(ApplicationComponent applicationComponent) {
        applicationComponent.loadModule(new HomeFeaturedModule(this)).inject(this);
    }

    @Override
    protected void initViews() {
        presenter.getEventFeaturedList();
    }

    @Override
    public void showEventList(List<Event> list) {
        viewPager.setAdapter(new HomeFeaturedFragment.PagerAdapter(getChildFragmentManager(), list));
    }



    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_home_featured, container, false);
    }

    private static class PagerAdapter extends FragmentPagerAdapter {

        List<Event> list ;

        private PagerAdapter(FragmentManager fragmentManager, List<Event> list) {
            super(fragmentManager);
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Fragment getItem(int position) {
            Event event = list.get(position);
            return HomeFeaturedItemFragment.newInstance(event);
        }
    }


    @Override
    public void onDestroyView() {
        presenter.cancelRequests();
        super.onDestroyView();
    }

}
