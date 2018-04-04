package com.belatrix.events.presentation.ui.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.view.WindowManager;

import com.belatrix.events.R;

import butterknife.ButterKnife;
import butterknife.OnClick;


/**
 * A simple {@link Fragment} subclass.
 */
public class QRDisplayActivity extends AppCompatActivity {


    public static void startActivity(Activity activity){
        Intent intent = new Intent(activity, QRDisplayActivity.class);
        activity.startActivity(intent);
        activity.overridePendingTransition(R.anim.anim_growing_in, R.anim.anim_growing_out);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr_display);
        ButterKnife.bind(this);
        getWindow().setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.MATCH_PARENT);
    }

    @OnClick(R.id.ok_button)
    public void onClickOK(){
        finish();
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_growing_in, R.anim.anim_growing_out);
    }
}
