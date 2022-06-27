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
    String location;
    String city;
    String state;

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

        JSONObject jObjectlocation = jsonObject.getJSONObject("_embedded");
        JSONArray jArraylocation = jObjectlocation.getJSONArray("venues");
        JSONObject json_data_location = jArraylocation.getJSONObject(0);
        location = json_data_location.getString("name");

        JSONObject jObjectCountry = jsonObject.getJSONObject("_embedded");
        JSONArray jArrayCountry = jObjectCountry.getJSONArray("venues");
        JSONObject json_data_firs = jArrayCountry.getJSONObject(0);
        JSONObject json_data_country = json_data_firs.getJSONObject("city");
        city = json_data_country.getString("name");

        JSONObject jObjectState = jsonObject.getJSONObject("_embedded");
        JSONArray jArrayState = jObjectState.getJSONArray("venues");
        JSONObject json_data_first = jArrayState.getJSONObject(0);
        JSONObject json_data_state = json_data_first.getJSONObject("state");
        state = json_data_state.getString("stateCode");

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
    public String getLocation() {
        return location;
    }
    public String getState() {
        return state;
    }

    public String getCity() {
        return city;
    }
}
