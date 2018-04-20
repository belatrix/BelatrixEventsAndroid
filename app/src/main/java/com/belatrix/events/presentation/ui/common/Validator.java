package com.belatrix.events.presentation.ui.common;

import android.content.Context;
import android.text.TextUtils;

import com.belatrix.events.R;

import java.util.Locale;

import javax.inject.Inject;
import javax.inject.Singleton;

public class Validator {

    private final static String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";
    private final Context mContext;

    @Inject
    public Validator(Context context) {
        this.mContext = context;
    }

    public String validateEmailField(String field, String value) {
        if (TextUtils.isEmpty(value)) {
            return String.format(Locale.getDefault(), mContext.getString(R.string.field_empty), field);
        } else if (!value.matches(emailPattern)) {
            return mContext.getString(R.string.email_invalid);
        }
        return "";
    }

    public String validateStringField(String field, String value) {
        if (TextUtils.isEmpty(value)) {
            return String.format(Locale.getDefault(), mContext.getString(R.string.field_empty), field);
        }
        return "";
    }
}
