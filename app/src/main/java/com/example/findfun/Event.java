package com.example.findfun;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.view.View;
import android.widget.Button;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Event {
    String eventName;
    String posterURL;
    String date;
    String location;
    String city;
    String state;

    public Event(String name, String image, String Date, String venue, String City, String State){
        this.eventName = name;
        this.posterURL = image;
        this.date = Date;
        this.location = venue;
        this.city = City;
        this.state = State;
    }
//
//    public Event(){
//    }
//    SQLiteDatabase db = this.getWritableDatabase();
//    ContentValues contentValues = new ContentValues();
//        contentValues.put(NAME_COL,event.getEventName());
//        contentValues.put(IMAGE_COL,event.getPosterUrl());
//        contentValues.put(DATE_COL, event.getDate());
//        contentValues.put(VENUE_COL,event.getLocation());
//        contentValues.put(CITY_COL,event.getCity());
//        contentValues.put(STATE_COL, event.getState());
//
//    long result  = db.insert(TABLE_NAME, null, contentValues);

    public Event(JSONObject jsonObject) throws JSONException {

//        locationgo = (Button) locationgo.findViewById(R.id.goToLocation);
//        locationgo.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent i = new Intent(MainActivity.this, SeeLocationActivity.class);
//                startActivity(i);
//            }
//        });
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

    }

    public static List<Event> fromJsonArray(JSONArray eventJsonArray) throws JSONException {
        List<Event> events = new ArrayList<>();
        for(int i=0; i<eventJsonArray.length(); i++){
            events.add(new Event(eventJsonArray.getJSONObject(i)));
        }
        return events;
    }

//    public static Comparator<Event> eventPriceHL = new Comparator<Event>() {
//        @Override
//        public int compare(Event e1, Event e2) {
//            return e1.getDate() - e2.getDate();
//        }
//    };
//
//    public static Comparator<Event> eventPriceLH = new Comparator<Event>() {
//        @Override
//        public int compare(Event e1, Event e2) {
//            return e1;
//        }
//    };

    public static Comparator<Event> DateAscending = new Comparator<Event>() {
        @Override
        public int compare(Event e1, Event e2) {
            return e1.getDate().compareTo(e2.getDate());
        }
    };

    public static Comparator<Event> DateDescending = new Comparator<Event>() {
        @Override
        public int compare(Event e1, Event e2) {
            return e2.getDate().compareTo(e1.getDate());
        }
    };

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
