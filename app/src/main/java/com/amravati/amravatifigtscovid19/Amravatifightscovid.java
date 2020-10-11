package com.amravati.amravatifigtscovid19;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class Amravatifightscovid extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
