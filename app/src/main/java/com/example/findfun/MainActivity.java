package com.example.findfun;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;
import android.widget.Toast;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.parse.ParseUser;

import java.util.Collections;

public class MainActivity extends AppCompatActivity {

    private BottomNavigationView bottomNavigationView;
    final FragmentManager fragmentManager = getSupportFragmentManager();
    Fragment fragment1 = new EventListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        Intent incomingIntent = getIntent();
        String eventType = incomingIntent.getStringExtra("Event");
        //String eventDate = incomingIntent.getStringExtra("Date");
        String eventCity = incomingIntent.getStringExtra("City");
        sendData(eventType, eventCity, fragment1);

        bottomNavigationView = findViewById(R.id.bottomNavigation);
        fragmentManager.beginTransaction().replace(R.id.flContainer, fragment1).addToBackStack(null).commit();

        bottomNavigationView.setOnItemSelectedListener(new BottomNavigationView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                Fragment fragment2 = null;
                switch (menuItem .getItemId()) {
                    case R.id.action_home:
                        fragment2 = new EventListFragment();
                        sendData(eventType, eventCity, fragment2);
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
    }

    private void sendData(String eventType, String eventCity, Fragment fragmentsent) {
        Bundle bundle = new Bundle();
        bundle.putString("Event",eventType);
        bundle.putString("City",eventCity);
        fragmentsent.setArguments(bundle);
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
}