package com.example.findfun;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.Spinner;

import java.security.PublicKey;
import java.util.Calendar;

public class CategoriesActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    public String sendCity;
    public String sendEvent;
    public String sendDate;
    Button btnGo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_categories);

        btnGo = findViewById(R.id.btnGo);

        initDatePicker();
        dateButton = findViewById(R.id.datePickerButton);
        dateButton.setText(getTodaysDate());

        Spinner citySpinner = (Spinner) findViewById(R.id.spinnerC);
        Spinner eventSpinner = (Spinner) findViewById(R.id.spinnerE);

        ArrayAdapter<String> myCAdapter = new ArrayAdapter<String>(CategoriesActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.city));
        myCAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        citySpinner.setAdapter(myCAdapter);

        ArrayAdapter<String> myEAdapter = new ArrayAdapter<String>(CategoriesActivity.this,
                android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.events));
        myEAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        eventSpinner.setAdapter(myEAdapter);

        citySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                String selectedCity = adapterView.getItemAtPosition(i).toString();
                switch (selectedCity)
                {
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

        btnGo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                sendDate = dateButton.getText().toString();
//                Bundle bundle = new Bundle();
//                bundle.putString("Event",sendEvent);
//                bundle.putString("Date",sendDate);
//                bundle.putString("City",sendCity);
//                EventListFragment fragobj = new EventListFragment();
//                fragobj.setArguments(bundle);
                //getSupportFragmentManager().beginTransaction().replace(R.id.flContainer, fragobj).commit();
                Intent intent = new Intent(CategoriesActivity.this, MainActivity.class);
                intent.putExtra("Event",sendEvent);
                intent.putExtra("Date",sendDate);
                intent.putExtra("City",sendCity);
                startActivity(intent);
            }
        });
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year)
    {
        return year + "-" + getMonthFormat(month) + "-" + day;
    }

    private String getMonthFormat(int month)
    {
        if(month == 1)
            return "01";
        if(month == 2)
            return "02";
        if(month == 3)
            return "03";
        if(month == 4)
            return "04";
        if(month == 5)
            return "05";
        if(month == 6)
            return "06";
        if(month == 7)
            return "07";
        if(month == 8)
            return "08";
        if(month == 9)
            return "09";
        if(month == 10)
            return "10";
        if(month == 11)
            return "11";
        if(month == 12)
            return "12";

        //default should never happen
        return "JAN";
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }
}