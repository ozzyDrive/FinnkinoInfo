package com.finnkinoinfo.finnkinoinfo.finnkinoApi;

public class Event {
    String name;
    int lengthInMinutes;
    int productionYear;
    String description;
    String ageRestriction;
    String thumbnail;
    String time;
    int eventId;

    int getRating() {
        return 0;
    }

    public String getName() {
        return name;
    }

    public int getLengthInMinutes() {
        return lengthInMinutes;
    }

    public int getProductionYear() {
        return productionYear;
    }

    public String getDescription() {
        return description;
    }

    public String getAgeRestriction() {
        return ageRestriction;
    }

    public String getThumbnail() {
        return thumbnail;
    }

    public String getTime() {
        return time;
    }
    public int getEventId(){
        return eventId;
    }
}
