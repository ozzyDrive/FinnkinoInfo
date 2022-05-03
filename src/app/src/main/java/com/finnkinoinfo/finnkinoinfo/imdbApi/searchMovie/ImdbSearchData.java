package com.finnkinoinfo.finnkinoinfo.imdbApi.searchMovie;

import java.util.List;

public class ImdbSearchData {
    public String searchType;
    public String expression;

    public List<ImdbSearchResult> results;

    public String errorMessage;
}
