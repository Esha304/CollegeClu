package com.example.findfun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.LinearLayout;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;


public class MyDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "eventsdb";
    public static final String TABLE_NAME = "MyEvents";
    public static final String NAME_COL = "Name";
    public static final String IMAGE_COL = "Image";
    public static final String DATE_COL = "Date";
    public static final String VENUE_COL = "Venue";
    public static final String CITY_COL = "City";
    public static final String STATE_COL = "State";

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String createTable = "CREATE TABLE " + TABLE_NAME +" ("
                + NAME_COL + " TEXT, "
                + IMAGE_COL + " TEXT, "
                + DATE_COL + " TEXT, "
                + VENUE_COL + " TEXT, "
                + CITY_COL + " TEXT, "
                + STATE_COL + " TEXT)";
        db.execSQL(createTable);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
         db.execSQL(new StringBuilder().append("DROP IF TABLE EXISTS ").append(TABLE_NAME).toString());
        onCreate(db);
    }


    public void addData(JSONArray givenArray){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for(int i=0; i<givenArray.length(); i++){
            JSONObject toAddObject;

            try {
                toAddObject = givenArray.getJSONObject(i);
                String name = toAddObject.getString("name");

                JSONArray jArrayimages = toAddObject.getJSONArray("images");
                JSONObject json_data_images = jArrayimages.getJSONObject(2);
                String image = json_data_images.getString("url");

                JSONObject jObjectdates = toAddObject.getJSONObject("dates");
                JSONObject json_data_date = jObjectdates.getJSONObject("start");
                String date = json_data_date.getString("localDate");

                JSONObject jObjectlocation = toAddObject.getJSONObject("_embedded");
                JSONArray jArraylocation = jObjectlocation.getJSONArray("venues");
                JSONObject json_data_location = jArraylocation.getJSONObject(0);
                String venue = json_data_location.getString("name");

                JSONObject jObjectCountry = toAddObject.getJSONObject("_embedded");
                JSONArray jArrayCountry = jObjectCountry.getJSONArray("venues");
                JSONObject json_data_firs = jArrayCountry.getJSONObject(0);
                JSONObject json_data_country = json_data_firs.getJSONObject("city");
                String city = json_data_country.getString("name");

                JSONObject jObjectState = toAddObject.getJSONObject("_embedded");
                JSONArray jArrayState = jObjectState.getJSONArray("venues");
                JSONObject json_data_first = jArrayState.getJSONObject(0);
                JSONObject json_data_state = json_data_first.getJSONObject("state");
                String state = json_data_state.getString("stateCode");

                contentValues.put(NAME_COL,name);
                contentValues.put(IMAGE_COL,image);
                contentValues.put(DATE_COL, date);
                contentValues.put(VENUE_COL,venue);
                contentValues.put(CITY_COL,city);
                contentValues.put(STATE_COL, state);


                Log.i(DATABASE_NAME, " inserting jsin data "+ name+" "+image+" "+date+" "+venue+" "+city+" "+state);

                db.insert(TABLE_NAME, null, contentValues);

            }catch (JSONException e){
                e.printStackTrace();
            }

            Log.i(DATABASE_NAME, " done inserting the data ");
        }

//        if(result == -1){
//            return false;
//        }else{
//            return true;
//        }
    }
//    public List<Event> getAllEvents(){
//        List<Event> returnlist = new ArrayList<>();
//        String queryString = "SELECT * FROM " + TABLE_NAME;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(queryString);
//        return returnlist;
//    }
}
