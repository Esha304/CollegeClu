package com.example.findfun;

import android.app.Application;

import com.parse.Parse;
import com.parse.ParseObject;

public class ParseApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
//        ParseObject.registerSubclass(Post.class);
//        ParseObject.registerSubclass(User.class);
//        ParseObject.registerSubclass(Comment.class);

        Parse.initialize(new Parse.Configuration.Builder(this)
                .applicationId("AtT1Ph84598MJ9HPORB40Tsjj0Rg3axDEtSAnE5F")
                .clientKey("Io45GM5Hm1pDUeQ2Tfmnxyl0tV1q0ulLskUZSXIt")
                .server("https://parseapi.back4app.com")
                .build()
        );

    }
}

