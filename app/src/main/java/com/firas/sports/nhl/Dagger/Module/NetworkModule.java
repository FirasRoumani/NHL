package com.firas.sports.nhl.Dagger.Module;

import android.content.Context;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.firas.sports.nhl.ModelLayer.Network.NhlApi;
import com.firas.sports.nhl.ModelLayer.Network.NhlApiImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class NetworkModule {
    @Provides
    @Singleton
    public RequestQueue provideRequestQueue(Context context) {
        return Volley.newRequestQueue(context);
    }

    @Provides
    @Singleton
    public NhlApi provideApi(Context context) {
        return new NhlApiImpl(context);
    }
}
