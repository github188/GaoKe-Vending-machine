package com.aircontrol.demo.ui;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.SystemClock;

import com.aircontrol.demo.Config;

import java.util.concurrent.TimeUnit;

public class SplashActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Config.writeToDebug("app Splash...");
        SystemClock.sleep(TimeUnit.SECONDS.toMillis(2));
        startActivity(new Intent(SplashActivity.this,FunctionActivity.class));
        finish();//结束此activity
    }
}
