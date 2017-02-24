package com.belatrixsf.events.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dvelasquez on 2/24/17.
 */

public class Event {

    private String name;

    public Event(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static List<Event> getDummyData(){
        List<Event> eventList = new ArrayList<>();
        for(int i = 1; i < 11 ; i++){
            eventList.add(new Event("Evento " + i));
        }
        return eventList;
    }
}
