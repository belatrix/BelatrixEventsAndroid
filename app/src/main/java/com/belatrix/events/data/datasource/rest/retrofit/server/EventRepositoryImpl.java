package com.belatrix.events.data.datasource.rest.retrofit.server;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;

import com.belatrix.events.data.datasource.common.Resource;
import com.belatrix.events.data.datasource.rest.retrofit.api.EventAPI;
import com.belatrix.events.data.datasource.rest.retrofit.base.BaseRepository;
import com.belatrix.events.data.datasource.rest.retrofit.response.RegisterAttendanceResponse;
import com.belatrix.events.domain.model.City;
import com.belatrix.events.domain.model.Contributor;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.model.Meeting;
import com.belatrix.events.domain.model.Project;
import com.belatrix.events.domain.model.Vote;
import com.belatrix.events.domain.repository.EventRepository;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.Executor;

import io.reactivex.Observable;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Response;

/**
 * Created by diegoveloper on 3/31/17.
 */

public class EventRepositoryImpl extends BaseRepository implements EventRepository {

    private final EventAPI eventAPI;
    private final Executor mExecutor;

    public EventRepositoryImpl(EventAPI eventAPI, Executor executor) {
        this.eventAPI = eventAPI;
        this.mExecutor = executor;
    }

    @Override
    public Observable<List<Contributor>> getHomeEvent() {
        //Call<List<Contributor>> call = eventAPI.repoContributors("square", "retrofit");
        //executeRequest(callBack, call);
        return null;
    }

    @Override
    public Observable<Event> featured(Integer cityId) {
        return subscribeOn(eventAPI.featured(cityId));
    }

    @Override
    public Observable<List<Event>> upcomingList(Integer cityId) {
        return subscribeOn(eventAPI.upcomingList(cityId));
    }

    @Override
    public Observable<List<Event>> pastList(Integer cityId) {
        return subscribeOn(eventAPI.pastList(cityId));
    }

    @Override
    public Observable<List<Project>> interactionList(int eventId) {
        return subscribeOn(eventAPI.interactionList(eventId));
    }

    @Override
    public Observable<List<Vote>> voteList(int eventId) {
        return subscribeOn(eventAPI.voteList(eventId));
    }

    @Override
    public Observable<Project> interactionVote(int interactionId) {
        return eventAPI.interactionVote(interactionId).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<City>> cityList() {
        return eventAPI.cityList().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public LiveData<Resource<List<Event>>> getListEvent(final Integer cityId) {
        final MutableLiveData<Resource<List<Event>>> data = new MutableLiveData<>();
        mExecutor.execute(new Runnable() {
            @Override
            public void run() {
                List<Event> events = new ArrayList<>();
                data.postValue(Resource.loading(events));
                // Get all upcoming events related to the city selected
                Response<List<Event>> responseUpcoming = null;
                try {
                    responseUpcoming = eventAPI.upcomingEvent(cityId).execute();
                } catch (IOException ioex) {
                    ioex.printStackTrace();
                    data.postValue(Resource.error(ioex.getMessage(), events));
                }
                // Get all past events related to the city selected
                Response<List<Event>> responsePast = null;
                try {
                    responsePast = eventAPI.pastEvent(cityId).execute();
                } catch (IOException ioex) {
                    ioex.printStackTrace();
                    data.postValue(Resource.error(ioex.getMessage(), events));
                }
                if (responseUpcoming != null && responsePast != null) {
                    events.addAll(responseUpcoming.body());
                    events.addAll(responsePast.body());
                    // Sort events by date and if they are upcoming
                    Collections.sort(events, new EventComparator());
                    data.postValue(Resource.success(events));
                }
            }
        });
        return data;
    }

    @Override
    public Observable<List<Meeting>> listMeetings(String token) {
        return eventAPI.listMeeting("Token " + token).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<RegisterAttendanceResponse> registerAttendance(String token, int meetingId, String email) {
        return eventAPI.registerAttendance("Token " + token, meetingId, email).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread());
    }

    @Override
    public Observable<List<Event>> listEvent(String token, int city) {
        return subscribeOn(eventAPI.listEvent("Token " + token, city));
    }

    private class EventComparator implements Comparator<Event> {

        @Override
        public int compare(Event o1, Event o2) {
            if (o1.isUpcoming() && o2.isUpcoming()) {
                if (o1.getDate().after(o2.getDate())) {
                    return -1;
                } else if (o1.getDate().before(o2.getDate())) {
                    return 1;
                } else {
                    return 0;
                }
            } else if (o1.isUpcoming() && !o2.isUpcoming()) {
                return -1;
            } else if (!o1.isUpcoming() && o2.isUpcoming()) {
                return 1;
            } else {
                if (o1.getDate().after(o2.getDate())) {
                    return -1;
                } else if (o1.getDate().before(o2.getDate())) {
                    return 1;
                } else {
                    return 0;
                }
            }
        }
    }
}
