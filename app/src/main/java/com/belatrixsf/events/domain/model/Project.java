package com.belatrixsf.events.domain.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dvelasquez on 2/24/17.
 */

public class Project {

    private String name;

    public Project(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static List<Project> getDummyData(){
        List<Project> projectList = new ArrayList<>();
        for(int i = 1; i < 21 ; i++){
            projectList.add(new Project("Proyecto " + i));
        }
        return projectList;
    }
}
