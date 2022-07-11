package com.example.findfun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.usage.UsageEvents;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.codepath.asynchttpclient.AsyncHttpClient;
import com.codepath.asynchttpclient.RequestParams;
import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.navigation.NavigationBarView;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment fragment1 = new EventListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment1).addToBackStack(null).commit();

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment2 = null;
                switch (menuItem .getItemId()) {
                    case R.id.action_home:
                        fragment2 = new EventListFragment();
                        break;
                    case R.id.action_Feed:
                        fragment2 = new FeedFragment();
                        break;
                    case R.id.action_compose:
                        fragment2 = new ComposeFragment();
                        break;
                    case R.id.action_settings:
                        //fragment = new ProfileFragment(ParseUser.getCurrentUser());
                        fragment2 = new SettingsFragment();
                    default:
                        break;
                }
                fragmentManager.beginTransaction().replace(R.id.flContainer, fragment2).addToBackStack(null).commit();
                return true;
            }
        });
//        editText = findViewById(R.id.edittext);
//        editText.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//                filter(s.toString());
//            }
//        });
    }

//    private void filter(String text) {
//        ArrayList<Event> filteredList = new ArrayList<>();
//        for (Event item : events) {
//            if (item.getCity().toLowerCase().contains(text.toLowerCase())) {
//                filteredList.add(item);
//            }
//        }
//        eventAdapter.filterList(filteredList);
//    }

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
//        if (item.getItemId() == R.id.action_search) {
//            populateSearchEvents("chicago");
//            return true;
//        }
//        if (item.getItemId() == R.id.rvEvents) {
//            //Intent i = new Intent(this, EventListActivity.class);
//            //startActivity(i);
//            return true;
//        }
        return super.onOptionsItemSelected(item);
    }

//    private void populateHomeTimeLine() {
//        EventClient client = new EventClient();
//        client.getHomeTimeline(new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Headers headers, JSON json) {
//                Log.d(TAG, "onSuccess");
//                JSONObject jsonObject = json.jsonObject;
//                try {
//                    JSONObject embedded = jsonObject.getJSONObject("_embedded");
//                    JSONArray results = embedded.getJSONArray("events");
//                    Log.i(TAG, "Results: " + results.toString());
//                    events.addAll(Event.fromJsonArray(results));
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
}