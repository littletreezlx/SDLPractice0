package com.example.ford_macbookair_1.sdlpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Bitmap;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

public class LockScreenActivity extends AppCompatActivity {

    public static final String LOCKSCREEN_BITMAP_EXTRA = "LOCKSCREEN_BITMAP_EXTRA";
    public static final String CLOSE_LOCK_SCREEN_ACTION = "CLOSE_LOCK_SCREEN";

    private final BroadcastReceiver closeLockScreenBroadcastReceiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            finish();
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        registerReceiver(closeLockScreenBroadcastReceiver, new IntentFilter(CLOSE_LOCK_SCREEN_ACTION));

        setContentView(R.layout.activity_lock_screen);

        Intent intent = getIntent();
        ImageView imageView = (ImageView) findViewById(R.id.lock_iv);

        if (intent.hasExtra(LOCKSCREEN_BITMAP_EXTRA)) {
            Bitmap lockscreen = (Bitmap) intent.getParcelableExtra(LOCKSCREEN_BITMAP_EXTRA);
            if (lockscreen != null) {
                imageView.setImageBitmap(lockscreen);
            }
        }
    }


    @Override
    protected void onDestroy(){
        super.onDestroy();
        unregisterReceiver(closeLockScreenBroadcastReceiver);
    }






}
