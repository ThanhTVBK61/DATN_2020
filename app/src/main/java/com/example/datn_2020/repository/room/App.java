package com.example.datn_2020.repository.room;

import android.app.Application;
import com.facebook.stetho.Stetho;

public class App extends Application {

    public static App INSTANCE;

    public void onCreate() {
        super.onCreate();

        INSTANCE = this;
        Stetho.initializeWithDefaults(this);
    }
}
