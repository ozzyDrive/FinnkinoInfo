package com.finnkinoinfo.finnkinoinfo;

public class recyclerView {
    String name;
    String hall;
    String time;


    public recyclerView(String name/*, String hall*/, String time) {
        this.name = name;
        this.hall = hall;
        //this.time = time;
    }

    public String getName() {
        return name;
    }

    public String getHall() {
        return hall;
    }

    public String getTime() {
        return time;
    }
}
