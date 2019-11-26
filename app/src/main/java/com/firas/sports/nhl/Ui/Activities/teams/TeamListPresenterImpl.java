package com.firas.sports.nhl.Ui.Activities.teams;

import android.content.Context;

import androidx.annotation.NonNull;

import com.android.volley.Response;
import com.firas.sports.nhl.Application.NhlApp;
import com.firas.sports.nhl.ModelLayer.Network.NhlApi;

import org.json.JSONException;

import javax.inject.Inject;

public class TeamListPresenterImpl implements TeamListPresenter {

    @Inject
    NhlApi mApi;

    private TeamListPresenter.View view;

    public TeamListPresenterImpl(Context context) {
        ((NhlApp) context).getAppComponent().inject(this);
    }

    @Override
    public void setView(View view) {
        this.view = view;
    }

    @Override
    public void getTeams() {
        view.showLoading();
        mApi.GetTeams(getResponseListener(), getErrorListener());
    }

    @NonNull
    private Response.Listener<String> getResponseListener() {
        return response -> {
            view.hideLoading();

            try {
                view.showTeams(mApi.parseTeamsResponse(response));
            } catch (JSONException error) {
                view.showError(error);
            }
        };
    }

    @NonNull
    private Response.ErrorListener getErrorListener() {
        return error -> {
            view.hideLoading();
            view.showError(error);
        };
    }
}
