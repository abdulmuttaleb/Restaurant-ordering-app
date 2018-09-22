package com.isaiko.hosnyorder;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class HosnyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
