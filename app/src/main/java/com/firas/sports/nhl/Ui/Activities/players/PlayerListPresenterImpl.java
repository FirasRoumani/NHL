package com.firas.sports.nhl.Ui.Activities.players;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Response;
import com.firas.sports.nhl.Application.NhlApp;
import com.firas.sports.nhl.ModelLayer.Network.NhlApi;

import org.json.JSONException;


import javax.inject.Inject;

public class PlayerListPresenterImpl implements PlayerListPresenter{

    @Inject NhlApi mApi;
    private View mView;

    public PlayerListPresenterImpl(Context context) {
        ((NhlApp)context).getAppComponent().inject(this);
    }

    @Override
    public void setView(View view) {
        this.mView = view;
    }

    @Override
    public void getPlayers(String url) {
        mView.showLoading();
        mApi.GetPlayers(url, getResponseListener(), getErrorListener());

    }

    @NonNull
    private Response.Listener<String> getResponseListener() {
        return response -> {
            mView.hideLoading();

            try {
                mView.showPlayers(mApi.parsePlayersResponse(response));
            } catch (JSONException error) {
                mView.showError(error);
            }
        };
    }

    @NonNull
    private Response.ErrorListener getErrorListener() {
        return error -> {
            mView.hideLoading();
            mView.showError(error);
        };
    }
}
