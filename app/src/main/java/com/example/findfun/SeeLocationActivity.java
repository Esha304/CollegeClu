package com.example.findfun;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ActivityNotFoundException;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.TextView;

public class SeeLocationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //setContentView(R.layout.activity_see_location);

        Bundle bundle = getIntent().getExtras();
        if (bundle != null) {
            String location = bundle.getString("Location");
            String city = bundle.getString("City");
            DisplayTrack(location, city);
//            TextView tvName = findViewById(R.id.tvName);
//            TextView tvAge = findViewById(R.id.tvAge);
//            tvName.setText(name);
//            tvAge.setText(age);
        }
    }

    private void DisplayTrack(String location, String city) {
        try {
            Uri uri = Uri.parse("https://www.google.com/maps/dir//"+ location + "," + city);
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setPackage("com.google.android.apps.maps");
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        } catch (ActivityNotFoundException e) {
            Uri uri = Uri.parse("https://play.google.com/store/apps/details?id=com.google.android.apps.maps");
            Intent intent = new Intent(Intent.ACTION_VIEW, uri);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
        }
    }
}