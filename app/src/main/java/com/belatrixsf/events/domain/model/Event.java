package com.belatrixsf.events.domain.model;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dvelasquez on 2/24/17.
 */

public class Event implements Parcelable{

    private int id;
    private String name;
    private String image;
    private String description;


    public Event(Parcel parcel){
        id = parcel.readInt();
        name = parcel.readString();
        image = parcel.readString();
        description = parcel.readString();
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Event(int id , String name, String image, String description) {
        this.id = id ;
        this.name = name;
        this.image = image;
        this.description = description;
    }

    public Event(int id , String name ,String description){
        this.id = id ;
        this.name = name;
        this.description = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }




    final static String[] imgArray = new String[] {"http://www.belatrixsf.com/images/Scrum_Masters_at_Hackatrix_Lima_2014.jpg","http://www.belatrixsf.com/images/Hackatrix_Lima_2016_developers_news.jpg","https://pbs.twimg.com/media/CvYHFKUWgAA33IR.jpg","https://i.ytimg.com/vi/ORcMWTwg-GM/maxresdefault.jpg" };
    final static String staticDescription = "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Nunc laoreet, orci id iaculis vulputate, dui sem hendrerit dui, nec mollis tellus nunc non odio. Fusce vulputate turpis venenatis tellus tincidunt, eu placerat ex accumsan. Vestibulum porta neque nec vulputate malesuada. Etiam porttitor a augue non iaculis. Suspendisse potenti. Nunc nec turpis et mauris tempor finibus. Curabitur non metus tristique erat congue porta ac quis mauris. Morbi sit amet enim neque. Morbi in ex pulvinar, vehicula urna in, consectetur est. Proin egestas condimentum enim at viverra. Aenean vitae risus ornare, dictum massa at, suscipit ipsum. Maecenas rutrum eros at nulla vulputate, in venenatis magna ultricies.";

    public static List<Event> getDummyEventListData(){
        List<Event> eventList = new ArrayList<>();
        for(int i = 1; i < 11 ; i++){
            if (i <= imgArray.length){
                eventList.add(new Event(i,"Evento " + i , imgArray[i-1],staticDescription));
            } else {
                eventList.add(new Event(i,"Evento " + i, staticDescription));
            }

        }
        return eventList;
    }

    public static List<Event> getDummyEventFeatureListData(){
        List<Event> eventList = new ArrayList<>();
        for(int i = 1; i < 2 ; i++){
            eventList.add(new Event(i,"Hackatrix Lima 2017" , imgArray[i-1],staticDescription));
        }
        return eventList;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int i) {
        dest.writeInt(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(description);
    }


    //creator - used when un-parceling our parcle (creating the object)
    public static final Parcelable.Creator<Event> CREATOR = new Parcelable.Creator<Event>(){

        @Override
        public Event createFromParcel(Parcel parcel) {
            return new Event(parcel);
        }

        @Override
        public Event[] newArray(int size) {
            return new Event[0];
        }
    };

}
