package com.firas.sports.nhl.Dagger.Component;



import com.firas.sports.nhl.Dagger.Module.AppModule;
import com.firas.sports.nhl.Dagger.Module.NetworkModule;
import com.firas.sports.nhl.Dagger.Module.UiModule;
import com.firas.sports.nhl.ModelLayer.Network.NhlApiImpl;
import com.firas.sports.nhl.Ui.Activities.MainActivity;
import com.firas.sports.nhl.Ui.Activities.player.PlayerDetails;
import com.firas.sports.nhl.Ui.Activities.player.PlayerPresenterImpl;
import com.firas.sports.nhl.Ui.Activities.players.PlayerListFragment;
import com.firas.sports.nhl.Ui.Activities.players.PlayerListPresenterImpl;
import com.firas.sports.nhl.Ui.Activities.teams.TeamListPresenterImpl;


import javax.inject.Singleton;

import dagger.Component;

@Singleton
@Component(modules = {AppModule.class, NetworkModule.class, UiModule.class})
public interface AppComponent {
    void inject(MainActivity target);
    void inject(PlayerDetails target);
    void inject(PlayerListFragment target);
    void inject(NhlApiImpl target);
    void inject(TeamListPresenterImpl target);
    void inject(PlayerListPresenterImpl target);
    void inject(PlayerPresenterImpl target);
}
