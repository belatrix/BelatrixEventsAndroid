package com.belatrix.events.domain.interactors;

import com.belatrix.events.data.datasource.rest.retrofit.response.RegisterAttendanceResponse;
import com.belatrix.events.domain.interactors.base.AbstractInteractor;
import com.belatrix.events.domain.model.User;
import com.belatrix.events.domain.repository.EventRepository;

import javax.inject.Inject;

import io.reactivex.functions.Consumer;
import okhttp3.ResponseBody;

public class RegisterAssistanceInteractor extends AbstractInteractor {

    private final EventRepository mEventRepository;

    @Inject
    RegisterAssistanceInteractor(EventRepository eventRepository) {
        this.mEventRepository = eventRepository;
    }

    public void registerAssistance(final Callback callback, String token, int meetingId, String email) {
        disposable = this.mEventRepository.registerAttendance(token, meetingId, email).subscribe(new Consumer<RegisterAttendanceResponse>() {
            @Override
            public void accept(RegisterAttendanceResponse response) throws Exception {
                callback.onRegistrationSuccessful(response.getUser());
            }
        }, new Consumer<Throwable>() {
            @Override
            public void accept(Throwable throwable) throws Exception {
                callback.onRegistrationFailed();
            }
        });
    }

    public interface Callback {
        void onRegistrationSuccessful(User user);

        void onRegistrationFailed();
    }
}
