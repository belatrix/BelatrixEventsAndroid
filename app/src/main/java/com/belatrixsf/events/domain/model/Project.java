package com.belatrixsf.events.domain.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by dvelasquez on 2/24/17.
 */

public class Project implements Comparable<Project> {

    private int id;
    private String name;
    private int votes;
    private transient boolean highest;

    public boolean isHighest() {
        return highest;
    }

    public void setHighest(boolean highest) {
        this.highest = highest;
    }

    public Project(int id, String name, int votes) {
        this.id = id;
        this.name = name;
        this.votes = votes;
    }

    public int getVotes() {
        return votes;
    }

    public void setVotes(int votes) {
        this.votes = votes;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public static List<Project> getDummyData(){
        Random random = new Random();
        List<Project> projectList = new ArrayList<>();
        for(int i = 1; i < 21 ; i++){
            projectList.add(new Project(i,"Proyecto "+ i ,getRandomInt(random,1,250)));
        }
        return projectList;
    }

    public static int getRandomInt(Random random, int min, int max)
    {
        return random.nextInt(max - min + 1) + min;
    }


    @Override
    public int compareTo(Project project) {
        return project.getVotes() - getVotes();
    }
}
