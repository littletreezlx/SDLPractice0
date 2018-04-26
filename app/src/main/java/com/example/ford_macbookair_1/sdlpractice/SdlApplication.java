package com.example.ford_macbookair_1.sdlpractice;

import android.app.Application;
import android.content.Intent;

public class SdlApplication extends Application{

//    private static int CONNECTION_TIMEOUT = 180 * 1000;

    @Override
    public void onCreate() {
        super.onCreate();

//        LockScreenActivity.registerActivityLifecycle(this);

        Intent proxyIntent = new Intent(this, SdlService.class);
        startService(proxyIntent);
    }

}
