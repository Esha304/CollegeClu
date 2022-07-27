package com.example.findfun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import okhttp3.Headers;

public class MonthActivity extends AppCompatActivity {

    public String sendDate;
    Button btnGo;
    String eventType;
    String eventCity;
    String useremail;
    MyDatabase eventDB;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_month);

        Intent incomingIntent = getIntent();
        useremail = ParseUser.getCurrentUser().getEmail();
        System.out.println("GOT " + useremail);
        Log.i("GOT ", "SUCCESSS " + useremail);
        eventType = incomingIntent.getStringExtra("Event");
        Log.i("GOT ", "SUCCESSS " + eventType);
        eventCity = incomingIntent.getStringExtra("City");
        Log.i("GOT ", "SUCCESSS " + eventCity);

        Spinner dateSpinner = (Spinner) findViewById(R.id.spinnerD);
        btnGo = findViewById(R.id.btnGo);
        eventDB = new MyDatabase(MonthActivity.this);

        ArrayAdapter<String> myDAdapter = new ArrayAdapter<String>(MonthActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.month));
        myDAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        dateSpinner.setAdapter(myDAdapter);

        dateSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedMonth = adapterView.getItemAtPosition(i).toString();
                switch (selectedMonth)
                {
                    case "January":
                        sendDate = "01";
                        break;

                    case "February":
                        sendDate = "02";
                        break;

                    case "March":
                        sendDate = "03";
                        break;

                    case "April":
                        sendDate = "04";
                        break;

                    case "May":
                        sendDate = "05";
                        break;

                    case "June":
                        sendDate = "06";
                        break;

                    case "July":
                        sendDate = "07";
                        break;

                    case "August":
                        sendDate = "08";
                        break;

                    case "September":
                        sendDate = "09";
                        break;

                    case "October":
                        sendDate = "10";
                        break;

                    case "November":
                        sendDate = "11";
                        break;

                    case "December":
                        sendDate = "12";
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
                populateEvents(eventCity, eventType);
                Intent intent = new Intent(MonthActivity.this, MainActivity.class);
                intent.putExtra("Email",useremail);
                intent.putExtra("Event",eventType);
                intent.putExtra("Date",sendDate);
                intent.putExtra("City",eventCity);
                startActivity(intent);
            }
        });
    }
    private void populateEvents(String location, String type) {
        EventClient client = new EventClient();
        client.getEventsOnCity(location, type, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("MonthActivity", "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject embedded = jsonObject.getJSONObject("_embedded");
                    JSONArray results = embedded.getJSONArray("events");
                    Log.i("MonthActivity", "Results: " + results.toString());

                    sendtoDb(results, useremail, location, type);
                    Log.i("SENT ", "SUCCESSS " + location + type + useremail);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("MonthActivity", "onFailure" + response);
            }
        });
    }

    private void sendtoDb(JSONArray results, String email, String location, String type) {
        if (eventDB.checkIfExists(email)) {
            eventDB.readAndUpdateData(results, email);
            Log.i("DATABASE UPDATE", "SUCCESSS " + location + type + email);
        }
        else{
            eventDB.addData(results, email);
            Log.i("DATABASE ADD", "SUCCESSS " + location + type + email);
        }
    }
}