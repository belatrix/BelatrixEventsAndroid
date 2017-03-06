package com.belatrixsf.events.presentation.ui.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.belatrixsf.events.R;
import com.belatrixsf.events.presentation.ui.base.BelatrixBaseActivity;

public class LoginActivity extends BelatrixBaseActivity  {

    public static final String LOGIN_PARAM = "login_param";
    public static final int IS_LOGGED = 1;
    public static final int IS_GUEST = 2;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public static Intent makeIntent(Context context) {
        return new Intent(context, LoginActivity.class);
    }

}
