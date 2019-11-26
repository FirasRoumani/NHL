package com.firas.sports.nhl.Ui.Activities.player;

import com.firas.sports.nhl.ModelLayer.Models.Player;

public interface PlayerPresenter {

    void setView(View view);
    void getPlayer(String url);

    interface View {
        void showLoading();
        void hideLoading();
        void showError(Exception exception);

        void showPlayer(Player player);
    }
}
