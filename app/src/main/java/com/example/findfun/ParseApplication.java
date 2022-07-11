package com.example.findfun;

import android.app.Application;
import com.parse.Parse;
import com.parse.ParseObject;

//public class ParseApplication extends Application {
//    @Override
//    public void onCreate() {
//        super.onCreate();
//        ParseObject.registerSubclass(Post.class);
////        ParseObject.registerSubclass(User.class);
////        ParseObject.registerSubclass(Comment.class);
//
//        Parse.initialize(new Parse.Configuration.Builder(this)
//                .applicationId(getString(R.string.back4app_app_id))
//                .clientKey(getString(R.string.back4app_client_key))
//                .server(getString(R.string.back4app_server_url))
//                .build());
//    }
//}

public class ParseApplication extends Application {

    // Initializes Parse SDK as soon as the application is created
    @Override
    public void onCreate() {
        super.onCreate();

        ParseObject.registerSubclass(Post.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("18veulvx2e4ryO5NpNhODz3MnEZufbesuhhQnIK4")
                .clientKey("4S4nZzh6SIrb1F0OlcIEt5jow3N0LHuVw9Igw7CD")
                .server("https://parseapi.back4app.com")
                .build()
        );
    }
}

