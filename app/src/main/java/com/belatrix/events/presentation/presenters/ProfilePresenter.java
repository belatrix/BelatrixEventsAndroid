package com.belatrix.events.presentation.presenters;

import android.graphics.Bitmap;

import com.belatrix.events.domain.interactors.GetProfileInteractor;
import com.belatrix.events.domain.model.Profile;
import com.belatrix.events.domain.model.User;
import com.belatrix.events.presentation.presenters.base.BelatrixBasePresenter;
import com.belatrix.events.presentation.presenters.base.BelatrixBaseView;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.journeyapps.barcodescanner.BarcodeEncoder;

import javax.inject.Inject;

public class ProfilePresenter extends BelatrixBasePresenter<ProfilePresenter.View> implements GetProfileInteractor.Callback {

    private final GetProfileInteractor mGetProfileInteractor;

    @Inject
    public ProfilePresenter(GetProfileInteractor getProfileInteractor) {
        mGetProfileInteractor = getProfileInteractor;
    }

    public void loadProfile(User user) {
        view.showEmail(user.getEmail());
        view.showFullName(user.getFullName());
        BarcodeEncoder barcodeEncoder = new BarcodeEncoder();
        try {
            Bitmap bitmap = barcodeEncoder.encodeBitmap(user.getEmail(), BarcodeFormat.QR_CODE, 400, 400);
            view.showQRCode(bitmap);
        } catch (WriterException wex) {
            wex.printStackTrace();
        }
        mGetProfileInteractor.getProfile(ProfilePresenter.this, user.getId());
    }

    @Override
    public void onRetrieveProfile(Profile profile) {
        if (profile.getLstParticipant() != null && profile.getLstParticipant().size() > 0) {
            for (Profile.ParticipantList participant : profile.getLstParticipant()) {
                view.showParcipantIn(participant.getIdea());
            }
        }
        if (profile.getLstCandidate() != null && profile.getLstCandidate().size() > 0) {
            for (Profile.CandidateList candidate : profile.getLstCandidate()) {
                view.showCandidateIn(candidate.getIdea());
            }
        }
        if (profile.getLstAttendance() != null && profile.getLstAttendance().size() > 0) {
            for (Profile.AttendanceList attendant : profile.getLstAttendance()) {
                view.showAssistedTo(attendant.getMeeting());
            }
        }
        if (profile.getLstEvents() != null && profile.getLstEvents().size() > 0) {
            for (Profile.EventList event : profile.getLstEvents()) {
                view.showMemberIn(event.getEvent());
            }
        }
    }

    @Override
    public void onRetrieveError() {

    }

    @Override
    public void cancelRequests() {
        mGetProfileInteractor.cancel();
    }

    public interface View extends BelatrixBaseView {
        void showQRCode(Bitmap bitmap);

        void showEmail(String email);

        void showFullName(String fullName);

        void showParcipantIn(Profile.Idea idea);

        void showCandidateIn(Profile.Idea idea);

        void showMemberIn(Profile.Event event);

        void showAssistedTo(Profile.Meeting meeting);
    }
}
