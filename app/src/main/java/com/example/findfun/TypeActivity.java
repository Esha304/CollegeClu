package com.example.findfun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.Toast;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import okhttp3.Headers;

public class TypeActivity extends AppCompatActivity {

    //public String sendDate;
    Button btnGo;
    String eventCity;
    String useremail;
    MyDatabase eventDB;
    public String sendEvent;
    ArrayList<String> event_email, event_name, event_image, event_date, event_venue, event_city, event_state;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getSupportActionBar().hide();
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_type);

        Intent incomingIntent = getIntent();
        useremail = ParseUser.getCurrentUser().getEmail();
        System.out.println("GOT in Type Activity -> " + useremail);
        eventCity = incomingIntent.getStringExtra("City");
        Log.i("GOT in Type Activity ", "succesfully "+ eventCity);

        event_email = new ArrayList<>();
        event_name = new ArrayList<>();
        event_image = new ArrayList<>();
        event_date = new ArrayList<>();
        event_venue = new ArrayList<>();
        event_city = new ArrayList<>();
        event_state = new ArrayList<>();
        event_email.add(useremail);

        Spinner eventSpinner = (Spinner) findViewById(R.id.spinnerE);
        btnGo = findViewById(R.id.btnshowEvent);
        eventDB = new MyDatabase(TypeActivity.this);

        ArrayAdapter<String> myEAdapter = new ArrayAdapter<String>(TypeActivity.this,
                R.layout.selecteditem_spinner, getResources().getStringArray(R.array.events));
        myEAdapter.setDropDownViewResource(R.layout.dropdown_spinner);
        eventSpinner.setAdapter(myEAdapter);

        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedEvent = adapterView.getItemAtPosition(i).toString();
                switch (selectedEvent)
                {
                    case "Sports":
                        sendEvent = "Sports";
                        break;

                    case "Music":
                        sendEvent = "Music";
                        break;

                    case "Arts and Theatre":
                        sendEvent = "art";
                        break;

                    case "Family":
                        sendEvent = "family";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updatetoDb(useremail, eventCity, sendEvent);
                Intent intent = new Intent(TypeActivity.this, MainActivity.class);
                intent.putExtra("Event",sendEvent);
                //intent.putExtra("Date",sendDate);
                intent.putExtra("City",eventCity);
                startActivity(intent);
            }
        });
    }

    private void updatetoDb(String email, String location, String type) {
        if (eventDB.checkIfExists(email)) {
            Cursor resDB = eventDB.getDataFromDB(location, type);
            if (resDB.getCount() == 0) {
                Log.d("TypeActivity", "onFailure with 0 count");
                return;
            }
            else{
                while (resDB.moveToNext()) {
                    event_name.add(resDB.getString(0));
                    event_image.add(resDB.getString(1));
                    event_date.add(resDB.getString(2));
                    event_venue.add(resDB.getString(3));
                    event_city.add(resDB.getString(4));
                    event_state.add(resDB.getString(5));
                }
                boolean isUpdated = eventDB.UpdateData(useremail, event_name, event_image, event_date, event_venue, event_city, event_state);
                if(isUpdated == true){
                    Log.i("DATABASE UPDATED", " from typeactivity SUCCESSS " + location + type + email);
                }
            }
        }
        else{
            Log.i("DATABASE ADD", " SHOULD NOT GO " + location + type + email);
        }
    }
}