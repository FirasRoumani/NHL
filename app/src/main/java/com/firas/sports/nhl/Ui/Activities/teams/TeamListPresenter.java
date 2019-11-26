package com.firas.sports.nhl.Ui.Activities.teams;

import com.firas.sports.nhl.ModelLayer.Models.Team;

import java.util.List;

public interface TeamListPresenter {

    void setView(View view);
    void getTeams();

    interface View {
        void showLoading();
        void hideLoading();
        void showError(Exception exception);
        void showTeams(List<Team> items);
        void launchTeamPlayer(Team item);
    }
}
