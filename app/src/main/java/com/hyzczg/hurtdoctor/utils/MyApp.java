package com.hyzczg.hurtdoctor.utils;

import android.app.Application;

/**
 * Created by 依萌 on 2017/5/8.
 */

public class MyApp extends Application {
    private static MyApp myApp;

    @Override
    public void onCreate() {
        super.onCreate();
        myApp = this;
    }

    public static MyApp getMyApp() {
        return myApp;
    }
}
