package com.firas.sports.nhl.Ui.Activities.players;

import com.firas.sports.nhl.ModelLayer.Models.Player;

import java.util.List;

public interface PlayerListPresenter {

    void setView(View view);
    void getPlayers(String url);

    interface View {
        void showLoading();
        void hideLoading();
        void showError(Exception exception);

        void showPlayers(List<Player> items);
    }
}
