package com.example.findfun;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class EventListFragment extends Fragment {

    public static final String TAG = "MainActivity";
    protected RecyclerView rvEvents;
    protected List<Event> events;
    protected EventAdapter eventAdapter;
    SwipeRefreshLayout swipeContainer;
    //protected String cityName;

    public EventListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        rvEvents = view.findViewById(R.id.rvEvents);
        events = new ArrayList<>();
        eventAdapter = new EventAdapter(getContext(), events);
        rvEvents.setAdapter(eventAdapter);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) { loadMoreData(); }
//        };
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
//        rvPosts.addOnScrollListener(scrollListener);

        if (getArguments() != null){
            String cityName = getArguments().getString("City");
            populateSearchEvents(cityName);
        }
        //Bundle bundle = this.getArguments();
        //String str = savedInstanceState.getString("City");
//        Intent intent = getIntent();
//        String str = intent.getStringExtra("City");
        populateSearchEvents("Chicago");

        //SwipeRefresh
        swipeContainer = view.findViewById(R.id.swipeContainer1);
        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
                android.R.color.holo_green_light,
                android.R.color.holo_orange_light,
                android.R.color.holo_red_light);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Log.i(TAG,"fetching new data");
                eventAdapter.clear();
                events.clear();
                populateSearchEvents("Chicago");
                swipeContainer.setRefreshing(false);
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