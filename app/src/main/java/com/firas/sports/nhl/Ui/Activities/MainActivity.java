package com.firas.sports.nhl.Ui.Activities;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firas.sports.nhl.Application.NhlApp;
import com.firas.sports.nhl.ModelLayer.Models.Team;
import com.firas.sports.nhl.ModelLayer.Network.NetworkErrorDescriptor;
import com.firas.sports.nhl.R;
import com.firas.sports.nhl.Ui.Activities.players.PlayerListFragment;
import com.firas.sports.nhl.Ui.Activities.teams.TeamListPresenter;
import com.firas.sports.nhl.Ui.Adapters.TeamsAdapter;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class MainActivity extends AppCompatActivity implements TeamListPresenter.View {

    @Inject
    TeamListPresenter mPresenter;
    @Inject
    SearchManager mSearchManager;

    private TeamsAdapter NavTeamListAdapter;

    private Toolbar toolbar;

    private DrawerLayout drawer;
    private ProgressBar mProgressBar;
    private RecyclerView teamsRecyclerView ;

    private boolean isDrawerOpen=false;
    private int selectedTeamPos=0;

    public SearchView searchView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.team_drawer);
        ((NhlApp)getApplication()).getAppComponent().inject(this);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(true);


        mProgressBar      =findViewById(R.id.progress_bar);
        teamsRecyclerView = findViewById(R.id.recycler_view_navigation);
        drawer            = findViewById(R.id.drawer_layout);

        mPresenter.setView(this);
        setUpNavigationView(new ArrayList<Team>());
        mPresenter.getTeams();
        setUpFragment();







    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (item.getItemId() == android.R.id.home) {
            if (isDrawerOpen) {
                drawer.closeDrawer(GravityCompat.START);
            } else {
                drawer.openDrawer(GravityCompat.START);
            }
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        if (!searchView.isIconified()) {
            searchView.setIconified(true);
            return;
        }
        super.onBackPressed();
    }

    private void setUpNavigationView(List<Team> teams) {
        GridLayoutManager manager = new GridLayoutManager(this,
                1);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(MainActivity.this.getApplicationContext());

        NavTeamListAdapter=new TeamsAdapter( MainActivity.this, R.layout.team_list_content);
        teamsRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        teamsRecyclerView.setItemAnimator(itemAnimator);
        teamsRecyclerView.setLayoutManager(manager);
        teamsRecyclerView.setAdapter(NavTeamListAdapter);
        teamsRecyclerView.setClickable(false);
        NavTeamListAdapter.setTeamsList(teams);
        NavTeamListAdapter.notifyDataSetChanged();

        NavTeamListAdapter.setOnItemClickListener(pos -> {
            selectedTeamPos=pos;
            Team selected_team = NavTeamListAdapter.getTeamsList().get(pos);
            launchTeamPlayer(selected_team);

        });

        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawer, toolbar, R.string.drawer_open, R.string.drawer_close) {

            @Override
            public void onDrawerClosed(View drawerView) {
                isDrawerOpen=false;
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                isDrawerOpen=true;
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank
                super.onDrawerOpened(drawerView);
            }
        };

        drawer.addDrawerListener(actionBarDrawerToggle);

        actionBarDrawerToggle.syncState();

    }

    private void setUpFragment(){
        Bundle bundle = new Bundle();
        bundle.putString(PlayerListFragment.BUNDLE_KEY_PLAYERS, "");

        PlayerListFragment playerListFragment = new PlayerListFragment();
        playerListFragment.setArguments(bundle);

        getSupportFragmentManager().beginTransaction()
                .add(R.id.player_container, playerListFragment)
                .commit();

    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.INVISIBLE);
    }

    @Override
    public void showError(Exception exception) {
        String message = NetworkErrorDescriptor.getDescription(this, exception);
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showTeams(List<Team> teams) {
        if(teams!=null && teams.size()>0) {
            NavTeamListAdapter.setTeamsList(teams);
            NavTeamListAdapter.notifyDataSetChanged();
            Bundle bundle = new Bundle();
            bundle.putString(PlayerListFragment.BUNDLE_KEY_PLAYERS,teams.get(0).getPlayers_link() );

            PlayerListFragment playerListFragment = new PlayerListFragment();
            playerListFragment.setArguments(bundle);


            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.player_container, playerListFragment)
                    .commit();
            setTitle();
        }
    }

    @Override
    public void launchTeamPlayer(Team team) {

        drawer.closeDrawers();


        Bundle bundle = new Bundle();
        bundle.putString(PlayerListFragment.BUNDLE_KEY_PLAYERS,team.getPlayers_link() );

        PlayerListFragment playerListFragment = new PlayerListFragment();
        playerListFragment.setArguments(bundle);


        getSupportFragmentManager().beginTransaction()
                .replace(R.id.player_container, playerListFragment)
                .commit();

        setTitle();

    }


    public void setTitle() {
        Objects.requireNonNull(getSupportActionBar()).setTitle(NavTeamListAdapter.getTeamsList().get(selectedTeamPos).getTeam_name());
    }
}
