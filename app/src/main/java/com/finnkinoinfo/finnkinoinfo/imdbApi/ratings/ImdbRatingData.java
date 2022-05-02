package com.finnkinoinfo.finnkinoinfo.imdbApi.ratings;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImdbRatingData {
    public float imDb;
}
