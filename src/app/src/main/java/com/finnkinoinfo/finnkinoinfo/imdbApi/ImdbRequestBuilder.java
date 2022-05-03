package com.finnkinoinfo.finnkinoinfo.imdbApi;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.ProtocolException;
import java.net.URL;

public class ImdbRequestBuilder {
    private final String apiKey = "k_lnuxskls";
    private final String apiBasePath = "https://imdb-api.com/en/API";

    private HttpURLConnection connection;
    private String urlString;

    public ImdbRequestBuilder() {
        urlString = apiBasePath;
    }

    public ImdbRequestBuilder withEndpoint(String endpoint) {
        urlString += endpoint;
        return this;
    }

    public ImdbRequestBuilder withApiKey() {
        urlString += "/" + apiKey;
        return this;
    }

    public ImdbRequestBuilder withPathParameter(String parameter) {
        urlString += "/" + parameter;
        return this;
    }

    public ImdbRequestBuilder createConnection() throws IOException {
        connection = (HttpURLConnection) new URL(urlString).openConnection();
        return this;
    }

    public ImdbRequestBuilder withMethod(String method) throws ProtocolException {
        connection.setRequestMethod(method);
        return this;
    }

    public HttpURLConnection build() {
        return connection;
    }
}
