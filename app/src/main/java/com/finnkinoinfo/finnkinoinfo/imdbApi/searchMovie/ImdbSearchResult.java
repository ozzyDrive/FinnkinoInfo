package com.finnkinoinfo.finnkinoinfo.imdbApi.searchMovie;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@JsonIgnoreProperties(ignoreUnknown = true)
public class ImdbSearchResult {
    public String id;
}
