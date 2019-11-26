package com.firas.sports.nhl.Ui.Activities.players;


import androidx.annotation.NonNull;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.SearchManager;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.firas.sports.nhl.Application.NhlApp;
import com.firas.sports.nhl.Helpers.AppPreferences;
import com.firas.sports.nhl.ModelLayer.Models.Player;
import com.firas.sports.nhl.ModelLayer.Network.NetworkErrorDescriptor;
import com.firas.sports.nhl.R;
import com.firas.sports.nhl.Ui.Activities.MainActivity;
import com.firas.sports.nhl.Ui.Activities.player.PlayerDetails;
import com.firas.sports.nhl.Ui.Adapters.PlayersAdapter;


import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

public class PlayerListFragment extends Fragment  implements PlayerListPresenter.View{

    @Inject
    PlayerListPresenter mPresenter;
    @Inject
    SearchManager mSearchManager;
    @Inject
    AppPreferences mAppPreferences;

    private RecyclerView playerRecyclerView;
    private ProgressBar mProgressBar;
    private PlayersAdapter PlayerListAdapter;
    private String roster;
    public static final String BUNDLE_KEY_PLAYERS = "BUNDLE_KEY_PLAYERS";

    public PlayerListFragment() {
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        ((NhlApp) Objects.requireNonNull(getActivity()).getApplication()).getAppComponent().inject(this);

        roster = getArguments() != null ? getArguments().getString(BUNDLE_KEY_PLAYERS) : "";

        mPresenter.setView(this);


    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        setHasOptionsMenu(true);
        View rootView = inflater.inflate(R.layout.fragment_player_list, container, false);

        mProgressBar=rootView.findViewById(R.id.progress_bar);
        playerRecyclerView = rootView.findViewById(R.id.recycler_view_players);


        setupRecyclerView(new ArrayList<>());
        if(roster!=null && !roster.isEmpty())
        mPresenter.getPlayers(roster);

        return rootView;
    }

    @Override
    public void onPrepareOptionsMenu(@NonNull Menu menu) {
        menu.clear();

         Objects.requireNonNull(getActivity()).getMenuInflater().inflate(R.menu.menu_player_list, menu);
        ((MainActivity)getActivity()).searchView = (SearchView) menu.findItem(R.id.action_search)
                .getActionView();
        ((MainActivity)getActivity()).searchView.setSearchableInfo(mSearchManager
                .getSearchableInfo(getActivity().getComponentName()));
        ((MainActivity)getActivity()).searchView.setMaxWidth(Integer.MAX_VALUE);

        // listening to search query text change
        ((MainActivity)getActivity()).searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                // filter recycler view when query submitted
                     mAppPreferences.hideSoftKeyboard(Objects.requireNonNull(getActivity()));
                     return true;
            }

            @Override
            public boolean onQueryTextChange(String query) {
                // filter recycler view when text is changed
                PlayerListAdapter.getFilter().filter(query);
                return true;
            }
        });

        super.onPrepareOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        switch (id){
            case R.id.action_search:
                return true;
            case R.id.action_sort_by_num:
                PlayerListAdapter.sortByNumber();
                return true;
            case R.id.action_sort_by_name:
                PlayerListAdapter.sortByName();
                return true;
        }

        return super.onOptionsItemSelected(item);
    }





    private void setupRecyclerView(List<Player> players)
    {
        GridLayoutManager manager = new GridLayoutManager(getActivity(), 1);

        PlayerListAdapter=new PlayersAdapter(R.layout.player_list_content);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(Objects.requireNonNull(getActivity()).getApplicationContext());
        playerRecyclerView.setLayoutManager(mLayoutManager);
        RecyclerView.ItemAnimator itemAnimator = new DefaultItemAnimator();
        itemAnimator.setAddDuration(1000);
        itemAnimator.setRemoveDuration(1000);
        playerRecyclerView.setItemAnimator(itemAnimator);
        playerRecyclerView.setLayoutManager(manager);
        playerRecyclerView.setAdapter(PlayerListAdapter);
        playerRecyclerView.setClickable(false);
        PlayerListAdapter.setPlayerList(players);
        PlayerListAdapter.notifyDataSetChanged();
        PlayerListAdapter.setOnItemClickListener(pos -> {
            Player selected_player = PlayerListAdapter.getPlayerList().get(pos);
            Intent intent = new Intent(getActivity(), PlayerDetails.class);
            intent.putExtra(PlayerDetails.BUNDLE_KEY_PLAYER, selected_player.getPlayerLink());
            startActivity(intent);

        });
    }

    @Override
    public void showLoading() {
        mProgressBar.setVisibility(View.VISIBLE);
    }

    @Override
    public void hideLoading() {
        mProgressBar.setVisibility(View.GONE);
    }

    @Override
    public void showError(Exception exception) {
        String message = NetworkErrorDescriptor.getDescription(getActivity(), exception);
        Toast.makeText(getActivity(), message, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void showPlayers(List<Player> players) {
        if(players!=null) {
            PlayerListAdapter.setPlayerList(players);
            PlayerListAdapter.notifyDataSetChanged();
        }
    }
}
