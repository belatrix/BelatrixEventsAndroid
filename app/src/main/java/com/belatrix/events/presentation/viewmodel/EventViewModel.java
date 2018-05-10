package com.belatrix.events.presentation.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModel;
import android.support.annotation.Nullable;

import com.belatrix.events.data.datasource.common.Resource;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.repository.EventRepository;

import java.util.List;

public class EventViewModel extends ViewModel {

    private EventRepository mEventRepository;
    private MediatorLiveData<Resource<List<Event>>> mMediatorListEvent;
    private LiveData<Resource<List<Event>>> mListEvent;

    public void init(EventRepository eventRepository, int cityId) {
        this.mEventRepository = eventRepository;
        mMediatorListEvent = new MediatorLiveData<>();
        mListEvent = mEventRepository.getListEvent(cityId);
        mMediatorListEvent.addSource(mListEvent, new Observer<Resource<List<Event>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Event>> resource) {
                mMediatorListEvent.setValue(resource);
            }
        });
    }

    public void refresh(int cityId) {
        mMediatorListEvent.removeSource(mListEvent);
        mListEvent = mEventRepository.getListEvent(cityId);
        mMediatorListEvent.addSource(mListEvent, new Observer<Resource<List<Event>>>() {
            @Override
            public void onChanged(@Nullable Resource<List<Event>> resource) {
                mMediatorListEvent.setValue(resource);
            }
        });
    }

    public LiveData<Resource<List<Event>>> getListEvent() {
        return mMediatorListEvent;
    }
}