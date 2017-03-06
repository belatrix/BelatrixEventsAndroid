package com.belatrixsf.events.presentation.ui.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrixsf.events.R;
import com.belatrixsf.events.di.component.UIComponent;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseFragment;

public  class AboutFragment extends BelatrixBaseFragment {



        public static AboutFragment newInstance() {
            AboutFragment aboutFragment = new AboutFragment();
            return aboutFragment;
        }


        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            return inflater.inflate(R.layout.fragment_about, container, false);
        }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
    }


    @Override
    protected void initViews() {

    }
}