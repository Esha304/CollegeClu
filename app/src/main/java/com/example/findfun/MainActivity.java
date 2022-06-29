package com.example.findfun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    public static final String TAG = "MainActivity";
    List<Event> events;
    EventAdapter eventAdapter;
    EditText editText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        editText = findViewById(R.id.edittext);
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                filter(s.toString());
            }
        });
        RecyclerView rvEvents = findViewById(R.id.rvEvents);
        events = new ArrayList<>();
        eventAdapter = new EventAdapter(this, events);
        rvEvents.setAdapter(eventAdapter);
        rvEvents.setLayoutManager(new LinearLayoutManager(this));
        populateHomeTimeLine();
    }

    private void filter(String text) {
        ArrayList<Event> filteredList = new ArrayList<>();
        for (Event item : events) {
            if (item.getCity().toLowerCase().contains(text.toLowerCase())) {
                filteredList.add(item);
            }
        }
        eventAdapter.filterList(filteredList);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

//        public void onClick(View v) {
//            int position = getAdapterPosition();
//            if (position != RecyclerView.NO_POSITION) {
//                Event event = events.get(position);
//                Intent intent = new Intent(context, EventDetailsActivity.class);
//                intent.putExtra(Event.class.getSimpleName(), Parcels.wrap(event));
//                context.startActivity(intent);
//            }
//        }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menulogoutbtn) {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            Intent i = new Intent(this, LoginActivity.class);
            startActivity(i);
            return true;
        }
        if (item.getItemId() == R.id.action_search) {
            populateSearchEvents("chicago");
            return true;
        }
//        if (item.getItemId() == R.id.rvEvents) {
//            //Intent i = new Intent(this, EventListActivity.class);
//            //startActivity(i);
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    private void populateHomeTimeLine() {
        EventClient client = new EventClient();
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject embedded = jsonObject.getJSONObject("_embedded");
                    JSONArray results = embedded.getJSONArray("events");
                    Log.i(TAG, "Results: " + results.toString());
                    events.addAll(Event.fromJsonArray(results));
                    eventAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure" + response);
            }
        });
    }

    private void populateSearchEvents(String location) {
        EventClient client = new EventClient();
        client.getEventsOnCity(location, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject embedded = jsonObject.getJSONObject("_embedded");
                    JSONArray results = embedded.getJSONArray("events");
                    Log.i(TAG, "Results: " + results.toString());
                    events.addAll(Event.fromJsonArray(results));
                    eventAdapter.notifyDataSetChanged();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d(TAG, "onFailure" + response);
            }
        });
    }
}