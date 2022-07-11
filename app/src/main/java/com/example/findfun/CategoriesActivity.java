package com.example.findfun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

public class CategoriesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        Spinner mySpinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<String> myAdapter = new ArrayAdapter<String>(CategoriesActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.names));
        myAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        mySpinner.setAdapter(myAdapter);

        mySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedCity = adapterView.getItemAtPosition(i).toString();
                switch (selectedCity)
                {
                    case "Albuquerque":
                        sendActivity("Albuquerque");
                        break;

                    case "Anaheim":
                        sendActivity("Anaheim");
                        break;

                    case "Arlington":
                        sendActivity("Arlington");
                        break;

                    case "Atlanta":
                        sendActivity("Atlanta");
                        break;

                    case "Austin":
                        sendActivity("Austin");
                        break;

                    case "Baltimore":
                        sendActivity("Baltimore");
                        break;

                    case "Boston":
                        sendActivity("Boston");
                        break;

                    case "Bristow":
                        sendActivity("Bristow");
                        break;

                    case "Bronx":
                        sendActivity("Bronx");
                        break;

                    case "Charlotte":
                        sendActivity("Charlotte");
                        break;

                    case "Chicago":
                        sendActivity("Chicago");
                        break;

                    case "Chula Vista":
                        sendActivity("Chula Vista");
                        break;

                    case "Cincinnati":
                        sendActivity("Cincinnati");
                        break;

                    case "Clarkston":
                        sendActivity("Clarkston");
                        break;

                    case "Cleveland":
                        sendActivity("Cleveland");
                        break;

                    case "Commerce City":
                        sendActivity("Commerce City");
                        break;

                    case "Dallas":
                        sendActivity("Dallas");
                        break;

                    case "Denver":
                        sendActivity("Denver");
                        break;

                    case "East Rutherford":
                        sendActivity("East Rutherford");
                        break;

                    case "Flushing":
                        sendActivity("Flushing");
                        break;

                    case "Glendale":
                        sendActivity("Glendale");
                        break;

                    case "Hershey":
                        sendActivity("Hershey");
                        break;

                    case "Holmdel":
                        sendActivity("Holmdel");
                        break;

                    case "Houston":
                        sendActivity("Houston");
                        break;

                    case "Indianapolis":
                        sendActivity("Indianapolis");
                        break;

                    case "Inglewood":
                        sendActivity("Inglewood");
                        break;

                    case "Jacksonville":
                        sendActivity("Jacksonville");
                        break;

                    case "Kansas City":
                        sendActivity("Kansas City");
                        break;

                    case "Las Vegas":
                        sendActivity("Las Vegas");
                        break;

                    case "Latrobe":
                        sendActivity("Latrobe");
                        break;

                    case "Los Angeles":
                        sendActivity("Los Angeles");
                        break;

                    case "Miami":
                        sendActivity("Miami");
                        break;

                    case "Minneapolis":
                        sendActivity("Minneapolis");
                        break;

                    case "Mountain View":
                        sendActivity("Mountain View");
                        break;

                    case "Nashville":
                        sendActivity("Nashville");
                        break;

                    case "New Orleans":
                        sendActivity("New Orleans");
                        break;

                    case "Orchard Park":
                        sendActivity("Orchard Park");
                        break;

                    case "Philadelphia":
                        sendActivity("Philadelphia");
                        break;

                    case "Pittsburgh":
                        sendActivity("Pittsburgh");
                        break;

                    case "Saint Louis":
                        sendActivity("Saint Louis");
                        break;

                    case "Salt Lake City":
                        sendActivity("Salt Lake City");
                        break;

                    case "Saint Petersburg":
                        sendActivity("Saint Petersburg");
                        break;

                    case "San Diego":
                        sendActivity("San Diego");
                        break;

                    case "San Francisco":
                        sendActivity("San Francisco");
                        break;

                    case "Santa Clara":
                        sendActivity("Santa Clara");
                        break;

                    case "Seattle":
                        sendActivity("Seattle");
                        break;
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });
    }

    private void sendActivity(String cityName) {
        Bundle bundle = new Bundle();
        bundle.putString("City",cityName);
        Fragment fragment = new EventListFragment();
        fragment.setArguments(bundle);
        //getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragment).commit();
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        //intent.putExtra("City", cityName);
        startActivity(intent);
    }
}