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
package com.belatrix.events.presentation.ui.base;

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;

import butterknife.BindString;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * @author PedroCarrillo
 * @author gyosida
 * @author dvelasquez
 * <p/>
 * BelatrixBaseFragment will implement the BelatrixBaseView interface and manage
 * common fragment stuff to avoid boilerplate code
 */
public abstract class BelatrixBaseFragment extends Fragment implements BelatrixBaseView {

    protected FragmentListener fragmentListener;
    @BindString(R.string.menu_title_share)
    protected String stringShare;
    @BindString(R.string.app_name)
    protected String stringAppName;
    private Unbinder unbinder;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        fragmentListener = (FragmentListener) context;
    }

    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        fragmentListener = (FragmentListener) activity;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        if (getActivity() instanceof BelatrixBaseActivity) {
            BelatrixBaseActivity baseActivity = (BelatrixBaseActivity) getActivity();
            initDependencies(baseActivity.getUiComponent());
        }
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        unbinder = ButterKnife.bind(this, view);
        super.onViewCreated(view, savedInstanceState);
        initViews();
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

    @Override
    public void onDetach() {
        super.onDetach();
        fragmentListener = null;
    }

    public void showProgressIndicator() {

    }

    public void hideProgressIndicator() {
    }

    @Override
    public void showProgressDialog() {
        if (fragmentListener != null) {
            fragmentListener.showProgressDialog();
        }
    }

    @Override
    public void showProgressDialog(String message) {
        if (fragmentListener != null) {
            fragmentListener.showProgressDialog(message);
        }
    }

    @Override
    public void dismissProgressDialog() {
        if (fragmentListener != null) {
            fragmentListener.dismissProgressDialog();
        }
    }

    @Override
    public void showSnackBar(String message) {
        if (fragmentListener != null) {
            fragmentListener.showSnackBar(message);
        }
    }

    @Override
    public void showSnackBar(View view, String message) {
        if (fragmentListener != null) {
            fragmentListener.showSnackBar(view, message);
        }
    }

    @Override
    public void showSnackBar(View view, String message, String action, View.OnClickListener onClickListener) {
        if (fragmentListener != null) {
            fragmentListener.showSnackBar(view, message, action, onClickListener);
        }
    }

    @Override
    public void showError(String message) {
        if (fragmentListener != null) {
            fragmentListener.showError(message);
        }
    }

    @Override
    public void setTitle(String title) {
        if (fragmentListener != null) {
            fragmentListener.setTitle(title);
        }
    }

    @Override
    public Context getContext() {
        return getActivity();
    }

    protected abstract void initDependencies(UIComponent applicationComponent);

    protected abstract void initViews();

    protected void replaceFragment(Fragment fragment, boolean addToBackStack) {
        fragmentListener.replaceFragment(fragment, addToBackStack);
    }

    protected void replaceChildFragment(Fragment fragment, int fragmentReplacedId) {
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        String tag = fragment.getClass().getSimpleName();
        transaction.replace(fragmentReplacedId, fragment, tag);
        transaction.commit();
    }

    public void refreshData() {

    }

}
