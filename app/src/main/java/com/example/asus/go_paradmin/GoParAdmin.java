package com.example.asus.go_paradmin;

import android.app.Application;
import android.content.Context;

/**
 * Created by adhit on 03/01/2018.
 */

public class GoParAdmin extends Application {
    private static Context sContext;
    public static Context getContext() {
        return sContext;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        sContext = this;
    }

    @Override
    public Context getApplicationContext() {
        return super.getApplicationContext();
    }

}
