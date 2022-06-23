package com.example.findfun;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Event {
    String eventName;
    String posterURL;
    String date;
//    String overview;
//    Double voteAverage;
//
//    public Movie() {}
//
//    public Double getVoteAverage() {
//        return voteAverage;
//    }
//
    public Event(JSONObject jsonObject) throws JSONException {
        eventName = jsonObject.getString("name");
        JSONArray jArrayimages = jsonObject.getJSONArray("images");
        JSONObject json_data_images = jArrayimages.getJSONObject(2);
        posterURL = json_data_images.getString("url");
        JSONObject jObjectdates = jsonObject.getJSONObject("dates");
        JSONObject json_data_date = jObjectdates.getJSONObject("start");
        date = json_data_date.getString("localDate");
//        title = jsonObject.getString("title");
//        overview = jsonObject.getString("overview");
//        voteAverage = jsonObject.getDouble("vote_average");
    }

    public static List<Event> fromJsonArray(JSONArray eventJsonArray) throws JSONException {
        List<Event> events = new ArrayList<>();
        for(int i=0; i<eventJsonArray.length(); i++){
            events.add(new Event(eventJsonArray.getJSONObject(i)));
        }
        return events;
    }

//    public String getPosterPath() {
//        return String.format("https://image.tmdb.org/t/p/w342/%s", posterPath);
//    }
//
//    public String getBackDropPath() {
//        return String.format("https://image.tmdb.org/t/p/w342/%s", backDropPath);
//    }

    public String getEventName() {
        return eventName;
    }

    public String getPosterUrl() {
        return posterURL;
    }

    public String getDate() {
        return date;
    }

//    public String getOverview() {
//        return overview;
//    }
}
