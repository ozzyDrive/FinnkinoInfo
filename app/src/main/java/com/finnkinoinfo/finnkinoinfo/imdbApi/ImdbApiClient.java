package com.finnkinoinfo.finnkinoinfo.imdbApi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.finnkinoinfo.finnkinoinfo.imdbApi.ratings.ImdbRatingData;
import com.finnkinoinfo.finnkinoinfo.imdbApi.searchMovie.ImdbSearchData;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;

public class ImdbApiClient {
    final String searchMovieEndpoint = "/Search";
    final String ratingsEndpoint = "/Ratings";

    public ImdbSearchData searchMovie(String name) throws IOException {
        HttpURLConnection connection = new ImdbRequestBuilder()
                .withEndpoint(searchMovieEndpoint)
                .withApiKey()
                .withPathParameter(name)
                .createConnection()
                .withMethod("GET")
                .build();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );

        ImdbSearchData searchData = new ObjectMapper()
                .readValue(reader.readLine(), ImdbSearchData.class);

        reader.close();
        connection.disconnect();

        return searchData;
    }

    public ImdbRatingData getRating(String movieId) throws IOException {
        HttpURLConnection connection = new ImdbRequestBuilder()
                .withEndpoint(ratingsEndpoint)
                .withApiKey()
                .withPathParameter(movieId)
                .createConnection()
                .withMethod("GET")
                .build();

        BufferedReader reader = new BufferedReader(
                new InputStreamReader(connection.getInputStream())
        );
        ImdbRatingData ratingData = new ObjectMapper()
                .readValue(reader.readLine(), ImdbRatingData.class);

        reader.close();
        connection.disconnect();

        return ratingData;
    }

}
