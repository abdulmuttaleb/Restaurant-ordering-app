package com.isaiko.restaurantordering;

import android.app.Application;

public class HosnyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
