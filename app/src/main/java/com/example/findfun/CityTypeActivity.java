package com.example.findfun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

public class CityTypeActivity extends AppCompatActivity {


    public static final String TAG = "CityTypeActivity";
    public String sendCity;
    public String sendEvent;
    Button btnNext;
    MyDatabase eventDB;
    String userEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_type);

//        Intent incomingIntent = getIntent();
//        userEmail = incomingIntent.getStringExtra("Email");
        userEmail = ParseUser.getCurrentUser().getEmail();
        System.out.println("GOT " + userEmail);

        btnNext = findViewById(R.id.btnNext);
        eventDB = new MyDatabase(CityTypeActivity.this);
        addDataFromApi();

//        initDatePicker();
//        dateButton = findViewById(R.id.datePickerButton);
//        dateButton.setText(getTodaysDate());

        Spinner citySpinner = (Spinner) findViewById(R.id.spinnerC);
        Spinner eventSpinner = (Spinner) findViewById(R.id.spinnerE);

        ArrayAdapter<String> myCAdapter = new ArrayAdapter<String>(CityTypeActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.city));
        myCAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(myCAdapter);

        ArrayAdapter<String> myEAdapter = new ArrayAdapter<String>(CityTypeActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.events));
        myEAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(myEAdapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedCity = adapterView.getItemAtPosition(i).toString();
                switch (selectedCity)
                {

                    case "Please select a city":
                        sendCity = "Please select a city";
                        break;

                    case "Albuquerque":
                        sendCity = "Albuquerque";
                        break;

                    case "Anaheim":
                        sendCity = "Anaheim";
                        break;

                    case "Arlington":
                        sendCity = "Arlington";
                        break;

                    case "Atlanta":
                        sendCity = "Atlanta";
                        break;

                    case "Austin":
                        sendCity = "Austin";
                        break;

                    case "Baltimore":
                        sendCity = "Baltimore";
                        break;

                    case "Boston":
                        sendCity = "Boston";
                        break;

                    case "Bristow":
                        sendCity = "Bristow";
                        break;

                    case "Bronx":
                        sendCity = "Bronx";
                        break;

                    case "Charlotte":
                        sendCity = "Charlotte";
                        break;

                    case "Chicago":
                        sendCity = "Chicago";
                        break;

                    case "Chula Vista":
                        sendCity = "Chula Vista";
                        break;

                    case "Cincinnati":
                        sendCity = "Cincinnati";
                        break;

                    case "Clarkston":
                        sendCity = "Clarkston";
                        break;

                    case "Cleveland":
                        sendCity = "Cleveland";
                        break;

                    case "Commerce City":
                        sendCity = "Commerce City";
                        break;

                    case "Dallas":
                        sendCity = "Dallas";
                        break;

                    case "Denver":
                        sendCity = "Denver";
                        break;

                    case "East Rutherford":
                        sendCity = "East Rutherford";
                        break;

                    case "Flushing":
                        sendCity = "Flushing";
                        break;

                    case "Glendale":
                        sendCity = "Glendale";
                        break;

                    case "Hershey":
                        sendCity = "Hershey";
                        break;

                    case "Holmdel":
                        sendCity = "Holmdel";
                        break;

                    case "Houston":
                        sendCity = "Houston";
                        break;

                    case "Indianapolis":
                        sendCity = "Indianapolis";
                        break;

                    case "Inglewood":
                        sendCity = "Inglewood";
                        break;

                    case "Jacksonville":
                        sendCity = "Jacksonville";
                        break;

                    case "Kansas City":
                        sendCity = "Kansas City";
                        break;

                    case "Las Vegas":
                        sendCity = "Las Vegas";
                        break;

                    case "Latrobe":
                        sendCity = "Latrobe";
                        break;

                    case "Los Angeles":
                        sendCity = "Los Angeles";
                        break;

                    case "Miami":
                        sendCity = "Miami";
                        break;

                    case "Minneapolis":
                        sendCity = "Minneapolis";
                        break;

                    case "Mountain View":
                        sendCity = "Mountain View";
                        break;

                    case "Nashville":
                        sendCity = "Nashville";
                        break;

                    case "New Orleans":
                        sendCity = "New Orleans";
                        break;

                    case "Orchard Park":
                        sendCity = "Orchard Park";
                        break;

                    case "Philadelphia":
                        sendCity = "Philadelphia";
                        break;

                    case "Pittsburgh":
                        sendCity = "Pittsburgh";
                        break;

                    case "Saint Louis":
                        sendCity = "Saint Louis";
                        break;

                    case "Salt Lake City":
                        sendCity = "Salt Lake City";
                        break;

                    case "Saint Petersburg":
                        sendCity = "Saint Petersburg";
                        break;

                    case "San Diego":
                        sendCity = "San Diego";
                        break;

                    case "San Francisco":
                        sendCity = "San Francisco";
                        break;

                    case "Santa Clara":
                        sendCity = "Santa Clara";
                        break;

                    case "Seattle":
                        sendCity = "Seattle";
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        eventSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedEvent = adapterView.getItemAtPosition(i).toString();
                switch (selectedEvent)
                {
                    case "Please select the type of event":
                        sendEvent = "Please select the type of event";
                        break;

                    case "Sports":
                        sendEvent = "sports";
                        break;

                    case "Music":
                        sendEvent = "music";
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


        btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(sendCity == "Please select a city" || sendEvent == "Please select the type of event"){
                    Toast.makeText(CityTypeActivity.this, "Please select the city and type of event!!", Toast.LENGTH_SHORT).show();
                }
                else{
                    Intent intent = new Intent(CityTypeActivity.this, MonthActivity.class);
                    intent.putExtra("Email", userEmail);
                    intent.putExtra("Event",sendEvent);
                    intent.putExtra("City",sendCity);
                    startActivity(intent);
                }
            }
        });
    }

    private void addDataFromApi() {
        eventDB.insertData();
    }
}