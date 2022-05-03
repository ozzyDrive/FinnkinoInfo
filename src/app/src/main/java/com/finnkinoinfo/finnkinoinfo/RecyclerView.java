package com.finnkinoinfo.finnkinoinfo;

public class RecyclerView {
    String name;
    int eventId;
    String time;

    /** Constructor makes new RecyclerView object whi parameter given*/
    public RecyclerView(String name, int eventId, String time) {
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
