package com.belatrixsf.events.presentation.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import com.belatrixsf.events.R;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseActivity;
import com.belatrixsf.events.presentation.ui.fragments.FinderFragment;

import butterknife.BindString;

public class FinderActivity extends BelatrixBaseActivity  {

    @BindString(R.string.menu_title_finder)
    String stringFinder;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_finder);
        setTitle(stringFinder);
        replaceFragment(FinderFragment.newInstance(),false);
        setNavigationToolbar();
    }

    public static Intent makeIntent(Activity context) {
        Intent intent = new Intent(context, FinderActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        context.overridePendingTransition(0, 0);
        return intent;
    }

}
