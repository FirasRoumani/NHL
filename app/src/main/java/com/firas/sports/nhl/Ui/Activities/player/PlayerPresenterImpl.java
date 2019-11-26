package com.firas.sports.nhl.Ui.Activities.player;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Response;
import com.firas.sports.nhl.Application.NhlApp;
import com.firas.sports.nhl.ModelLayer.Network.NhlApi;

import org.json.JSONException;

import javax.inject.Inject;

public class PlayerPresenterImpl implements PlayerPresenter{


    @Inject NhlApi mApi;
    private View mView;

    public PlayerPresenterImpl(Context context) {
        ((NhlApp)context).getAppComponent().inject(this);
    }
    @Override
    public void setView(View view) {
        this.mView = view;
    }

    @Override
    public void getPlayer(String url) {
        mView.showLoading();
        mApi.GetPlayerDetail(url, getResponseListener(), getErrorListener());
    }

    @NonNull
    private Response.Listener<String> getResponseListener() {
        return response -> {
            mView.hideLoading();

            try {
                mView.showPlayer(mApi.parsePlayerDetailResponse(response));
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
