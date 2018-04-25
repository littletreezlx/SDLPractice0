package com.example.ford_macbookair_1.sdlpractice;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;

public class SdlRouterService extends com.smartdevicelink.transport.SdlRouterService {
    public SdlRouterService() {
    }


    @Override
    public void onCreate() {
        super.onCreate();
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {
        return super.onStartCommand(intent, flags, startId);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }





}
