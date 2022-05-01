package com.finnkinoinfo.finnkinoinfo.finnkinoApi;

import com.finnkinoinfo.finnkinoinfo.imdbApi.ImdbApiClient;

import java.io.IOException;

public class Event {
    String name;
    String id;
    int lengthInMinutes;
    int productionYear;
    String description;
    String ageRestriction;
    String thumbnail;
    String time;
    int eventId;

    ImdbApiClient imdbApiClient;

    public Event(ImdbApiClient imdbApiClient) {
        this.imdbApiClient = imdbApiClient;
    }

    /***
     * Fetches the movie's rating value from ImDb API
     * @return integer value of rating
     * @throws IOException
     */
    int getRating() throws IOException {
        assert id != null;
        return imdbApiClient.getRating(id).imDb;
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
