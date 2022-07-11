package com.example.findfun;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

public class EventClient {
    // https://app.ticketmaster.com/discovery/v2/events.json?city=Los Angeles&apikey=uG8OMmifp1HVa3Joyhm1EEhFmK6dyNwc
    private static final String API_BASE_URL = "https://app.ticketmaster.com/discovery/v2/events.json?city=";
    private static final String API_LAST_URL = "&apikey=uG8OMmifp1HVa3Joyhm1EEhFmK6dyNwc";
    public static final String NOW_PLAYING_URL = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=uG8OMmifp1HVa3Joyhm1EEhFmK6dyNwc";
    public AsyncHttpClient client;

    public EventClient() {
        this.client = new AsyncHttpClient();
    }

    private String getApiCityUrl(String choosenCity) {
        return API_BASE_URL + choosenCity + API_LAST_URL;
    }

    public void getHomeTimeline(JsonHttpResponseHandler handler) {
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("size", 200);
        client.get(NOW_PLAYING_URL, params, handler);
    }

    // Method for accessing the search API
    public AsyncHttpClient getEventsOnCity(String location, JsonHttpResponseHandler handler) {
        String apiCUrl = getApiCityUrl(location);
        //String url = getApiCityUrl("Los Angeles");
        //client.get(getApiCityUrl(location), handler);
        RequestParams params = new RequestParams();
        params.put("size", 200 );
        client.get(apiCUrl, params, handler);
        return client;
    }
}
