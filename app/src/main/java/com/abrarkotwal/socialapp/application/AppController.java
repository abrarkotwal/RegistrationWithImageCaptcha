package com.abrarkotwal.socialapp.application;

import android.app.Application;


import com.abrarkotwal.socialapp.application.ApiBuilder.AppComponent;
import com.abrarkotwal.socialapp.application.ApiBuilder.AppContextModule;
import com.abrarkotwal.socialapp.application.ApiBuilder.DaggerAppComponent;

import timber.log.BuildConfig;
import timber.log.Timber;

public class AppController extends Application {


    private static AppComponent appComponent;


    @Override
    public void onCreate() {
        super.onCreate();
        initialiseLogger();
        initAppComponent();
    }

    private void initAppComponent() {
        appComponent = DaggerAppComponent.builder().appContextModule(new AppContextModule(this)).build();
    }


    private void initialiseLogger() {
        if (BuildConfig.DEBUG) {
            Timber.plant(new Timber.DebugTree());
        } else {
            Timber.plant(new Timber.Tree() {
                @Override
                protected void log(int priority, String tag, String message, Throwable t) {
                    //TODO  decide what to log in release version
                }
            });
        }
    }

    public static AppComponent getAppComponent() {
        return appComponent;
    }

}
