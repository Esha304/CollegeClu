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
import com.example.findfun.Event;
import com.example.findfun.EventAdapter;
import com.example.findfun.EventClient;
import com.example.findfun.R;

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
    String strEvent;
    String strDate;
    String strCity;

    public EventListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            strEvent = getArguments().getString("Event");
            strDate = getArguments().getString("Date");
            strCity = getArguments().getString("City");
            System.out.println("Eventfrag "+ strCity+strEvent);
            populateSearchEvents(strCity, strEvent);
        }

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

        //populateSearchEvents(strCity, strEvent);

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
                populateSearchEvents(strCity, strEvent);
                swipeContainer.setRefreshing(false);
            }
        });

    }

    private void populateSearchEvents(String location, String type) {
        EventClient client = new EventClient();
        client.getEventsOnCity(location, type, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d(TAG, "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject embedded = jsonObject.getJSONObject("_embedded");
                    JSONArray results = embedded.getJSONArray("events");
                    Log.i(TAG, "Results: " + results.toString());
                    events.addAll(Event.fromJsonArray(results));
//                    ArrayList<Event> filteredList = new ArrayList<>();
//                    for (Event item : events) {
//                        if (item.getDate().contains(strDate)) {
//                            filteredList.add(item);
//                        }
//                    }
//                    eventAdapter.filterList(filteredList);
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