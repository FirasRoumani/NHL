package com.firas.sports.nhl.Application;

import android.app.Application;

import com.blongho.country_data.World;
import com.firas.sports.nhl.Dagger.Component.AppComponent;
import com.firas.sports.nhl.Dagger.Module.AppModule;
import com.firas.sports.nhl.Dagger.Component.DaggerAppComponent;

public class NhlApp extends Application {
    protected AppComponent appComponent;

    public AppComponent getAppComponent() {
        return appComponent;
    }

    protected AppComponent initDagger(NhlApp application) {
        return DaggerAppComponent.builder()
                .appModule(new AppModule(application))
                .build();
    }

    @Override
    public void onCreate() {
        super.onCreate();
        World.init(this);
        appComponent = initDagger(this);
    }
}