package com.belatrix.events.presentation.ui.fragments;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;

import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;

public class RecoverPasswordFragment extends BelatrixBaseFragment {

    public static Fragment newInstance(Context context) {
        Bundle args = new Bundle();
        return Fragment.instantiate(context, RecoverPasswordFragment.class.getName(), args);
    }

    @Override
    protected void initDependencies(UIComponent uiComponent) {
        uiComponent.inject(this);
    }

    @Override
    protected void initViews() {

    }
}
