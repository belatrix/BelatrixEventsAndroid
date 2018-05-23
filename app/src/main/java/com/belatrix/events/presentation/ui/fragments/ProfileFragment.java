package com.belatrix.events.presentation.ui.fragments;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.belatrix.events.R;
import com.belatrix.events.di.component.UIComponent;
import com.belatrix.events.domain.model.Profile;
import com.belatrix.events.domain.model.User;
import com.belatrix.events.presentation.presenters.ProfilePresenter;
import com.belatrix.events.presentation.ui.base.BelatrixBaseFragment;

import javax.inject.Inject;

import butterknife.BindView;

public class ProfileFragment extends BelatrixBaseFragment implements ProfilePresenter.View {

    private static final String ARGS_USER = "args_user";

    @BindView(R.id.iv_qr_code)
    ImageView ivQRCode;
    @BindView(R.id.tv_email)
    TextView tvEmail;
    @BindView(R.id.tv_fullname)
    TextView tvFullName;
    @BindView(R.id.ll_participant_in)
    LinearLayout llParticipantIn;
    @BindView(R.id.ll_candidate_in)
    LinearLayout llCandidateIn;
    @BindView(R.id.ll_member_in)
    LinearLayout llMemberIn;
    @BindView(R.id.ll_assisted_to)
    LinearLayout llAssistedTo;

    @Inject
    ProfilePresenter mPresenter;

    private LayoutInflater inflater;

    public static Fragment create(Context context, User user) {
        Bundle args = new Bundle();
        args.putParcelable(ARGS_USER, user);
        return Fragment.instantiate(context, ProfileFragment.class.getName(), args);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        inflater = LayoutInflater.from(getContext());
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_profile, container, false);
    }

    @Override
    protected void initDependencies(UIComponent applicationComponent) {
        applicationComponent.inject(ProfileFragment.this);
        mPresenter.setView(ProfileFragment.this);
    }

    @Override
    protected void initViews() {
        setTitle(getString(R.string.menu_title_profile));
    }

    @Override
    public void onStart() {
        super.onStart();
        if (getArguments() != null) {
            Bundle args = getArguments();
            User user = args.getParcelable(ARGS_USER);
            mPresenter.loadProfile(user);
        }
    }

    @Override
    public void showQRCode(Bitmap bitmap) {
        ivQRCode.setImageBitmap(bitmap);
    }

    @Override
    public void showEmail(String email) {
        tvEmail.setText(email);
    }

    @Override
    public void showFullName(String fullName) {
        tvFullName.setText(fullName);
    }

    @Override
    public void showParcipantIn(Profile.Idea idea) {
        TextView tvName = (TextView) inflater.inflate(R.layout.item_profile_event, llParticipantIn, false);
        tvName.setText(idea.getTitle());
        llParticipantIn.addView(tvName);
    }

    @Override
    public void showCandidateIn(Profile.Idea idea) {
        TextView tvName = (TextView) inflater.inflate(R.layout.item_profile_event, llCandidateIn, false);
        tvName.setText(idea.getTitle());
        llCandidateIn.addView(tvName);
    }

    @Override
    public void showMemberIn(Profile.Event event) {
        TextView tvName = (TextView) inflater.inflate(R.layout.item_profile_event, llMemberIn, false);
        tvName.setText(event.getTitle());
        llMemberIn.addView(tvName);
    }

    @Override
    public void showAssistedTo(Profile.Meeting meeting) {
        TextView tvName = (TextView) inflater.inflate(R.layout.item_profile_event, llAssistedTo, false);
        tvName.setText(meeting.getName());
        llAssistedTo.addView(tvName);
    }
}
