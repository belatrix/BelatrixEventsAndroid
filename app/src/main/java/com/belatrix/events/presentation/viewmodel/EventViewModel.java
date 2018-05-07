package com.belatrix.events.presentation.viewmodel;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.ViewModel;

import com.belatrix.events.data.datasource.common.Resource;
import com.belatrix.events.domain.model.Event;
import com.belatrix.events.domain.repository.EventRepository;

import java.util.List;

public class EventViewModel extends ViewModel {

    private EventRepository mEventRepository;
    private LiveData<Resource<List<Event>>> mListEvent;

    public void init(EventRepository eventRepository, int cityId) {
        this.mEventRepository = eventRepository;
        mListEvent = mEventRepository.getListEvent(cityId);
    }

    public void loadEvents(int cityId) {
        mListEvent = mEventRepository.getListEvent(cityId);
    }

    public LiveData<Resource<List<Event>>> getListEvent() {
        return mListEvent;
    }
}
