package com.belatrix.events.presentation.ui.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Meeting;
import com.belatrix.events.presentation.presenters.RegisterAssistancePresenter;
import com.belatrix.events.presentation.ui.activities.ScannerActivity;
import com.belatrix.events.presentation.ui.adapters.MeetingListAdapter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;
import com.belatrix.events.presentation.ui.common.DividerItemDecoration;
import com.google.zxing.integration.android.IntentIntegrator;

import java.util.List;

import javax.inject.Inject;

import butterknife.BindView;

public class RegisterAssistanceFragment extends BelatrixBaseFragment implements RegisterAssistancePresenter.View, MeetingListAdapter.OnItemPressedListener {

    @BindView(R.id.rv_events)
    RecyclerView rvEvents;

    @Inject
    RegisterAssistancePresenter mPresenter;

    MeetingListAdapter mAdapter;

    public static Fragment create(Context context) {
        Bundle args = new Bundle();
        return Fragment.instantiate(context, RegisterAssistanceFragment.class.getName(), args);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_register_assistance, container, false);
    }

    @Override
    protected void initDependencies(UIComponent applicationComponent) {
        applicationComponent.inject(RegisterAssistanceFragment.this);
        mPresenter.setView(RegisterAssistanceFragment.this);
    }

    @Override
    protected void initViews() {
        rvEvents.post(new Runnable() {
            @Override
            public void run() {
                setTitle(getString(R.string.menu_title_register_assistance));
            }
        });
        mAdapter = new MeetingListAdapter(RegisterAssistanceFragment.this);
        rvEvents.setAdapter(mAdapter);
        if (getContext() != null) {
            rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
            rvEvents.addItemDecoration(new DividerItemDecoration(ContextCompat.getDrawable(getContext(), android.R.drawable.divider_horizontal_bright)));
            mPresenter.loadEventsForModerator();
        }
    }

    @Override
    public void loadEventsSuccessful(List<Meeting> result) {
        mAdapter.addAll(result);
    }

    @Override
    public void loadEventsError() {

    }

    @Override
    public void onItemPressed(Meeting meeting) {
        IntentIntegrator integrator = new IntentIntegrator(getActivity())
                .setDesiredBarcodeFormats(IntentIntegrator.QR_CODE)
                .setCaptureActivity(ScannerActivity.class)
                .setOrientationLocked(false);
        Intent intent = integrator.createScanIntent();
        intent.putExtra(ScannerActivity.ARGS_MEETING_ID, meeting.getId());
        startActivity(intent);
    }
}
