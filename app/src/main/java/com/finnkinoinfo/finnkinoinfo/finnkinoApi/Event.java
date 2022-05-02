package com.finnkinoinfo.finnkinoinfo.finnkinoApi;

import com.finnkinoinfo.finnkinoinfo.imdbApi.ImdbApiClient;
import com.finnkinoinfo.finnkinoinfo.imdbApi.ratings.ImdbRatingData;
import com.finnkinoinfo.finnkinoinfo.imdbApi.searchMovie.ImdbSearchData;
import com.finnkinoinfo.finnkinoinfo.imdbApi.searchMovie.ImdbSearchResult;

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
    String place;

    ImdbApiClient imdbApiClient;

    public Event(ImdbApiClient imdbApiClient) {
        this.imdbApiClient = imdbApiClient;
    }

    /***
     * Fetches the movie's rating value from ImDb API
     * @return float value of rating
     * @throws IOException
     */
    public float getRating() throws IOException {
        assert name != null;
        ImdbSearchData searchData = imdbApiClient.searchMovie(name);
        if (searchData.results.size() > 0) {
            ImdbSearchResult searchResult = searchData.results.get(0);
            ImdbRatingData ratingData = imdbApiClient.getRating(searchResult.id);
            return ratingData.imDb;
        }
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
    public String getPlace(){
        return place;
    }
}
