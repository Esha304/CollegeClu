package com.example.findfun;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import com.parse.ParseUser;

import java.util.ArrayList;

public class EventListFragment extends Fragment {

    public static final String TAG = "MainActivity";
    protected RecyclerView rvEvents;
    //protected List<Event> events;
    protected EventAdapter eventAdapter;
    //SwipeRefreshLayout swipeContainer;
    String userEmail;
    String strEvent;
    //String strDate;
    String strCity;
    MyDatabase eventDB;
    Button btnviewAll;
    ArrayList<String> event_email, event_name, event_image, event_date, event_venue, event_city, event_state;
    int count;

    public EventListFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        if (getArguments() != null) {
            userEmail = ParseUser.getCurrentUser().getEmail();
            System.out.println("GOT in eventfrag " + userEmail);
            strEvent = getArguments().getString("Event");
            System.out.println("GOT in eventfrag " + strEvent);
            //strDate = getArguments().getString("Date");
            strCity = getArguments().getString("City");
            System.out.println("GOT in eventfrag " + strCity);
            //populateSearchEvents(strCity, strEvent);
        }

        return inflater.inflate(R.layout.fragment_event_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        super.onViewCreated(view, savedInstanceState);

        eventDB = new MyDatabase(getContext());
        event_email = new ArrayList<>();
        event_name = new ArrayList<>();
        event_image = new ArrayList<>();
        event_date = new ArrayList<>();
        event_venue = new ArrayList<>();
        event_city = new ArrayList<>();
        event_state = new ArrayList<>();
        event_email.add(userEmail);
        btnviewAll = view.findViewById(R.id.btn_showmore);
        rvEvents = view.findViewById(R.id.rvEvents);
        viewData();
        //events = new ArrayList<>();
//        eventAdapter = new EventAdapter(getContext(), event_name, event_image, event_date, event_venue, event_city, event_state);
//        rvEvents.setAdapter(eventAdapter);
        //LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getContext());
//        scrollListener = new EndlessRecyclerViewScrollListener(linearLayoutManager) {
//            @Override
//            public void onLoadMore(int page, int totalItemsCount, RecyclerView view) { loadMoreData(); }
//        };
//        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
//        rvPosts.addOnScrollListener(scrollListener);

        //populateSearchEvents(strCity, strEvent);

        //SwipeRefresh
//        swipeContainer = view.findViewById(R.id.swipeContainer1);
//        swipeContainer.setColorSchemeResources(android.R.color.holo_blue_bright,
//                android.R.color.holo_green_light,
//                android.R.color.holo_orange_light,
//                android.R.color.holo_red_light);
//        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
//            @Override
//            public void onRefresh() {
//                Log.i(TAG,"fetching new data");
//                eventAdapter.clear();
//                events.clear();
//                populateSearchEvents(strCity, strEvent);
//                swipeContainer.setRefreshing(false);
//            }
//        });
        btnviewAll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                count = count+1;
                Cursor res = eventDB.getNextData();
                if (count > 3) {
                    Toast.makeText(getContext(), "There are no more events", Toast.LENGTH_SHORT).show();
                    return;
                }
                else{
                    while (res.moveToNext()) {
                        event_email.add(res.getString(0));
                        event_name.add(res.getString(1));
                        event_image.add(res.getString(2));
                        event_date.add(res.getString(3));
                        event_venue.add(res.getString(4));
                        event_city.add(res.getString(5));
                        event_state.add(res.getString(6));
                    }
                    eventAdapter = new EventAdapter(getContext(), event_name, event_image, event_date, event_venue, event_city, event_state);
                    rvEvents.setAdapter(eventAdapter);
                    rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
                    eventAdapter.notifyDataSetChanged();
                }
            }
        });
    }

    private void viewData() {
        if (eventDB.checkIfExists(userEmail)) {
            Cursor resDB = eventDB.getAllData(userEmail);
            if (resDB.getCount() == 0) {
                Toast.makeText(getContext(), "No data found while going through database ", Toast.LENGTH_SHORT).show();
                return;
            }
            while (resDB.moveToNext()) {
                event_email.add(resDB.getString(0));
                event_name.add(resDB.getString(1));
                event_image.add(resDB.getString(2));
                event_date.add(resDB.getString(3));
                event_venue.add(resDB.getString(4));
                event_city.add(resDB.getString(5));
                event_state.add(resDB.getString(6));
            }
            eventAdapter = new EventAdapter(getContext(), event_name, event_image, event_date, event_venue, event_city, event_state);
            rvEvents.setAdapter(eventAdapter);
            rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
            Log.d("eventlistfrag", " done inserting the data from DB ");
        }
        else{
            Log.d("eventlistfrag", " data not found ");
        }
    }

    //    private void populateSearchEvents(String location, String type) {
//        EventClient client = new EventClient();
//        client.getEventsOnCity(location, type, new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Headers headers, JSON json) {
//                Log.d(TAG, "onSuccess");
//                JSONObject jsonObject = json.jsonObject;
//                try {
//                    JSONObject embedded = jsonObject.getJSONObject("_embedded");
//                    JSONArray results = embedded.getJSONArray("events");
//                    Log.i(TAG, "Results: " + results.toString());
//
//                    sendtoDb(results);
//
//                    events.addAll(Event.fromJsonArray(results));
//                    ArrayList<Event> filteredList = new ArrayList<>();
//                    for (Event item : events) {
//                        if (item.getDate().contains(strDate)) {
//                            filteredList.add(item);
//                        }
//                    }
//                    eventAdapter.filterList(filteredList);
//                    eventAdapter.notifyDataSetChanged();
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//            @Override
//            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
//                Log.d(TAG, "onFailure" + response);
//            }
//        });
//    }
    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_main, menu);
        inflater.inflate(R.menu.menu_sort, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == R.id.menulogoutbtn) {
            ParseUser.logOut();
            ParseUser currentUser = ParseUser.getCurrentUser();
            Intent i = new Intent(getContext(), LoginActivity.class);
            startActivity(i);
            return true;
        }
        if (item.getItemId() == R.id.menubackbtn) {
            Intent i = new Intent(getContext(), CityActivity.class);
            startActivity(i);
            return true;
        }
        if (item.getItemId() == R.id.DateRO) {
            Toast.makeText(getActivity(), "You selected date HL ", Toast.LENGTH_SHORT).show();
            SortDateRO();
            return true;
        }
        if (item.getItemId() == R.id.DateOR) {
            Toast.makeText(getActivity(), "You selected date LH ", Toast.LENGTH_SHORT).show();
            SortDateOR();
            return true;
        }
//        if (item.getItemId() == R.id.Ascen) {
//
//            Toast.makeText(getActivity(), "You selected ascending ", Toast.LENGTH_SHORT).show();
//            return true;
//        }
//        if (item.getItemId() == R.id.Descen) {
//            Toast.makeText(getActivity(), "You selected descending ", Toast.LENGTH_SHORT).show();
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

    private void SortDateOR() {
        Cursor res = eventDB.sortDateOR();
        while (res.moveToNext()) {
            event_email.add(res.getString(0));
            event_name.add(res.getString(1));
            event_image.add(res.getString(2));
            event_date.add(res.getString(3));
            event_venue.add(res.getString(4));
            event_city.add(res.getString(5));
            event_state.add(res.getString(6));
        }
        eventAdapter = new EventAdapter(getContext(), event_name, event_image, event_date, event_venue, event_city, event_state);
        rvEvents.setAdapter(eventAdapter);
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        Toast.makeText(getActivity(), "You sorted date ", Toast.LENGTH_SHORT).show();
        eventAdapter.notifyDataSetChanged();
    }

    private void SortDateRO() {
        Cursor res = eventDB.sortDateRO();
        while (res.moveToNext()) {
            event_email.add(res.getString(0));
            event_name.add(res.getString(1));
            event_image.add(res.getString(2));
            event_date.add(res.getString(3));
            event_venue.add(res.getString(4));
            event_city.add(res.getString(5));
            event_state.add(res.getString(6));
        }
        eventAdapter = new EventAdapter(getContext(), event_name, event_image, event_date, event_venue, event_city, event_state);
        rvEvents.setAdapter(eventAdapter);
        rvEvents.setLayoutManager(new LinearLayoutManager(getContext()));
        Toast.makeText(getActivity(), "You sorted date ", Toast.LENGTH_SHORT).show();
        eventAdapter.notifyDataSetChanged();
    }
}