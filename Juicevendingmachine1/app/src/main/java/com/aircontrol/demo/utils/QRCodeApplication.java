package com.aircontrol.demo.utils;

import android.app.Application;

/**
 * Created by 大东 on 2017/7/21 0021.
 */

public class QRCodeApplication extends Application {

    private static QRCodeApplication qrCodeApplication;

    public static QRCodeApplication getInstance() {
        return qrCodeApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        qrCodeApplication = this;
    }
}
