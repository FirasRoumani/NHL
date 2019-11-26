package com.firas.sports.nhl.Dagger.Module;



import android.content.Context;

import com.firas.sports.nhl.Ui.Activities.player.PlayerPresenter;
import com.firas.sports.nhl.Ui.Activities.player.PlayerPresenterImpl;
import com.firas.sports.nhl.Ui.Activities.players.PlayerListPresenter;
import com.firas.sports.nhl.Ui.Activities.players.PlayerListPresenterImpl;
import com.firas.sports.nhl.Ui.Activities.teams.TeamListPresenter;
import com.firas.sports.nhl.Ui.Activities.teams.TeamListPresenterImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;

@Module
public class UiModule {

    @Provides
    @Singleton
    public TeamListPresenter provideTeamPresenter(Context context) {
        return new TeamListPresenterImpl(context);
    }

    @Provides
    @Singleton
    public PlayerListPresenter providePlayersPresenter(Context context) {
        return new PlayerListPresenterImpl(context);
    }

    @Provides
    @Singleton
    public PlayerPresenter providePlayerDetailPresenter(Context context) {
        return new PlayerPresenterImpl(context);
    }

}
