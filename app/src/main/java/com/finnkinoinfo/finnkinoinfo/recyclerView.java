package com.finnkinoinfo.finnkinoinfo;

public class recyclerView {
    String name;
    int eventId;
    String time;


    public recyclerView(String name,int eventId, String time) {
        this.name = name;
        this.eventId = eventId;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public int getId() {
        return eventId;
    }

    public String getTime() {
        return time;
    }
}
