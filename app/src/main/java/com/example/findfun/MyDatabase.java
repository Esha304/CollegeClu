package com.example.findfun;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.LinearLayout;

import com.codepath.asynchttpclient.callback.JsonHttpResponseHandler;
import com.parse.ParseUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import okhttp3.Headers;


public class MyDatabase extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "eventsdb";
    public static final String TABLE_1 = "APIEvents";
    public static final String TABLE_2 = "MyEvents";
    public static final String EMAIL_COL = "Email";
    public static final String NAME_COL = "Name";
    public static final String IMAGE_COL = "Image";
    public static final String DATE_COL = "Date";
    public static final String VENUE_COL = "Venue";
    public static final String CITY_COL = "City";
    public static final String STATE_COL = "State";
    public static final String TYPE_COL = "Type";
    String userEmail;
    int nexttimes = 5;

    public MyDatabase(Context context) {
        super(context, DATABASE_NAME, null, 1);
        userEmail = ParseUser.getCurrentUser().getEmail();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

        String createTable1 = "CREATE TABLE " + TABLE_1 +" ("
                + NAME_COL + " TEXT, "
                + IMAGE_COL + " TEXT, "
                + DATE_COL + " TEXT, "
                + VENUE_COL + " TEXT, "
                + CITY_COL + " TEXT, "
                + STATE_COL + " TEXT, "
                + TYPE_COL + " TEXT)";

        String createTable2 = "CREATE TABLE " + TABLE_2 +" ("
                + EMAIL_COL + " TEXT, "
                + NAME_COL + " TEXT, "
                + IMAGE_COL + " TEXT, "
                + DATE_COL + " TEXT, "
                + VENUE_COL + " TEXT, "
                + CITY_COL + " TEXT, "
                + STATE_COL + " TEXT)";

        db.execSQL(createTable1);
        db.execSQL(createTable2);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_1);
        db.execSQL("DROP TABLE IF EXISTS "+TABLE_2);
        onCreate(db);
    }

    public void insertData() {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        EventClient client = new EventClient();
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Headers headers, JSON json) {
                Log.d("MyDatabase", "onSuccess");
                JSONObject jsonObject = json.jsonObject;
                try {
                    JSONObject embedded = jsonObject.getJSONObject("_embedded");
                    JSONArray results = embedded.getJSONArray("events");
                    Log.i("MyDatabase", "Results: " + results.toString());


                    for(int i=0; i<results.length(); i++){
                        JSONObject toAddObject;

                        try {
                            toAddObject = results.getJSONObject(i);
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

                            JSONObject jObjectType = toAddObject.getJSONObject("_embedded");
                            JSONArray jArrayType = jObjectType.getJSONArray("attractions");
                            JSONObject json_data_firsty = jArrayType.getJSONObject(0);
                            JSONArray json_dataA_Type = json_data_firsty.getJSONArray("classifications");
                            JSONObject json_data_secondy = json_dataA_Type.getJSONObject(0);
                            JSONObject json_dataO_Type = json_data_secondy.getJSONObject("segment");
                            String type = json_dataO_Type.getString("name");

                            contentValues.put(NAME_COL,name);
                            contentValues.put(IMAGE_COL,image);
                            contentValues.put(DATE_COL, date);
                            contentValues.put(VENUE_COL,venue);
                            contentValues.put(CITY_COL,city);
                            contentValues.put(STATE_COL, state);
                            contentValues.put(TYPE_COL, type);

                            Log.i(DATABASE_NAME, " inserting jsin data "+ name+" "+image+" "+date+" "+venue+" "+city+" "+state + " "+type);

                            db.insert(TABLE_1, null, contentValues);

                        }catch (JSONException e){
                            e.printStackTrace();
                        }
                        Log.i(DATABASE_NAME, " done inserting the data ");
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            @Override
            public void onFailure(int statusCode, Headers headers, String response, Throwable throwable) {
                Log.d("MyDatabase", "onFailure" + response);
            }
        });
    }


    public void addData(JSONArray givenArray, String emailId){
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();

        for(int i=0; i<givenArray.length(); i++){
            JSONObject toAddObject;

            try {

                String email = emailId;

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

                contentValues.put(EMAIL_COL,email);
                contentValues.put(NAME_COL,name);
                contentValues.put(IMAGE_COL,image);
                contentValues.put(DATE_COL, date);
                contentValues.put(VENUE_COL,venue);
                contentValues.put(CITY_COL,city);
                contentValues.put(STATE_COL, state);


                Log.i(DATABASE_NAME, " inserting json data "+ name+" "+image+" "+date+" "+venue+" "+city+" "+state);

                db.insert(TABLE_2, null, contentValues);

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

    public boolean checkIfExists(String userEmail){
        SQLiteDatabase db = this.getReadableDatabase();

        String query = "select "+ EMAIL_COL + " from " + TABLE_2;
        Cursor cursor = db.rawQuery(query, null);
        String existEmail;

        if (cursor.moveToFirst()) {
            do {
                existEmail = cursor.getString(0);

                if (existEmail.equals(userEmail)) {
                    return true;
                }
            } while (cursor.moveToNext());
        }
        return false;
    }

    public Cursor getAllData(String email) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("Select * from " + TABLE_2 + " WHERE "+ EMAIL_COL + " ='" + email + "'"+ " LIMIT " + nexttimes, null);;
//        if(db != null){
//            cursor = db.rawQuery(query, null);
//        }
        return res;
    }

    public Cursor getDataFromDB(String city, String type) {
        SQLiteDatabase db = this.getWritableDatabase();

        Cursor res = db.rawQuery("Select * from " + TABLE_1 + " WHERE "+ CITY_COL + " ='"+ city + "'" + " AND " + TYPE_COL + " ='"+ type + "'", null);
//        if(db != null){
//            cursor = db.rawQuery(query, null);
//        }
        return res;
    }

    public Cursor getNextData() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_2 + " WHERE "+ EMAIL_COL + " ='" + userEmail + "'"+ " LIMIT " + nexttimes + " OFFSET " + 5, null);
        nexttimes=nexttimes+5;
        return res;
    }

    public Cursor sortDateRO() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_2+ " ORDER BY "+ DATE_COL + " ASC", null);
        return res;
    }

    public Cursor sortDateOR() {
        SQLiteDatabase db = this.getWritableDatabase();
        Cursor res = db.rawQuery("SELECT * FROM " + TABLE_2+ " ORDER BY "+ DATE_COL + " DESC", null);
        return res;
    }

    public boolean UpdateData(String email, ArrayList<String> event_name, ArrayList<String> event_image, ArrayList<String> event_date, ArrayList<String> event_venue, ArrayList<String> event_city, ArrayList<String> event_state) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        for(int i=0; i<event_city.size(); i++){
            contentValues.put(EMAIL_COL,email);
            contentValues.put(NAME_COL,String.valueOf(event_name.get(i)));
            contentValues.put(IMAGE_COL,String.valueOf(event_image.get(i)));
            contentValues.put(DATE_COL, String.valueOf(event_date.get(i)));
            contentValues.put(VENUE_COL,String.valueOf(event_venue.get(i)));
            contentValues.put(CITY_COL,String.valueOf(event_city.get(i)));
            contentValues.put(STATE_COL, String.valueOf(event_state.get(i)));
        }
        db.update(TABLE_2, contentValues, "Email = ?",new String[] {email});
        Log.i("DATABASE UPDATED ", "SUCCESSS ");
        return true;
    }

//    public List<Event> getAllEvents(){
//        List<Event> returnlist = new ArrayList<>();
//        String queryString = "SELECT * FROM " + TABLE_NAME;
//        SQLiteDatabase db = this.getReadableDatabase();
//        Cursor cursor = db.rawQuery(queryString);
//        return returnlist;
//    }
}
