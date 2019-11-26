package com.firas.sports.nhl.ModelLayer.Network;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;

import com.android.volley.Cache;
import com.android.volley.NoConnectionError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.firas.sports.nhl.Application.NhlApp;
import com.firas.sports.nhl.Helpers.Constants;
import com.firas.sports.nhl.ModelLayer.Models.Player;
import com.firas.sports.nhl.ModelLayer.Models.Team;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import javax.inject.Inject;

public class NhlApiImpl implements NhlApi {
    @Inject
    ConnectivityManager mConnectivityManager;
    @Inject
    RequestQueue requestQueue;


    public NhlApiImpl(Context context) {
        ((NhlApp)context).getAppComponent().inject(this);
    }



    @Override
    public void GetTeams(Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        Uri uri = buildTeamsUri();
        doQuery(responseListener, errorListener, uri);

    }

    @Override
    public void GetPlayers(String teamUrl, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        Uri uri = buildPlayersUri(teamUrl);
        doQuery(responseListener, errorListener, uri);

    }

    @Override
    public void GetPlayerDetail(String playerDetailUrl, Response.Listener<String> responseListener, Response.ErrorListener errorListener) {
        Uri uri = buildPlayerDetailsUri(playerDetailUrl);
        doQuery(responseListener, errorListener, uri);

    }

    @Override
    public List<Team> parseTeamsResponse(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        JSONArray array = json.getJSONArray(Constants.JSON_KEY_TEAMS);

        List<Team> teams = new LinkedList<>();
        for (int i = 0; i < array.length(); ++i) {
            JSONObject team = array.getJSONObject(i);

            int team_id=team.getInt("id");
            String team_name=team.getString("name");
            String team_logo_url=Constants.NHL_TEAM_LOGO_URL+team.getInt("id")+Constants.NHL_TEAM_LOGO_EXT;
            String players_link=team.getString("link");
            teams.add(new Team( team_id,  team_name,  team_logo_url, players_link));
        }

        return teams;
    }

    @Override
    public List<Player> parsePlayersResponse(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        JSONArray array = json.getJSONArray(Constants.JSON_KEY_PLAYERS);


        List<Player> players = new ArrayList<Player>();
        for (int i = 0; i < array.length(); ++i) {
            JSONObject roster = array.getJSONObject(i);
            JSONObject player=roster.getJSONObject("person");
            JSONObject player_position=roster.getJSONObject("position");

            int     player_id = player.getInt("id");
            String  name = player.getString("fullName");
            String  playerLink= player.getString("link");
            int     player_num=roster.getInt("jerseyNumber");
            String   position = player_position.getString("type");

            players.add(new Player( player_id,  name,  player_num,  position,  null, playerLink));

        }


        return players;
        }



    @Override
    public Player parsePlayerDetailResponse(String jsonString) throws JSONException {
        JSONObject json = new JSONObject(jsonString);
        JSONArray array = json.getJSONArray(Constants.JSON_KEY_PEOPLE);

        Player currentPlayer=null;
        for (int i = 0; i < array.length(); ++i) {
            JSONObject player = array.getJSONObject(i);

            JSONObject player_position = player.getJSONObject("primaryPosition");
            int player_id = player.getInt("id");
            int player_num = player.getInt("primaryNumber");
            String name = player.getString("fullName");
            String playerLink = player.getString("link");
            String position = player_position.getString("type");
            String player_nationality = player.getString("nationality");

            currentPlayer=new Player(player_id, name, player_num, position, player_nationality, playerLink);
        }
        return currentPlayer;
    }

    private void doQuery(Response.Listener<String> responseListener,
                         Response.ErrorListener errorListener,
                         Uri uri) {
        if (isNetworkAvailable()) {
            doNetworkQuery(responseListener, errorListener, uri);
        } else {
            doCachedQuery(responseListener, errorListener, uri);
        }
    }
    private void doNetworkQuery(Response.Listener<String> responseListener,
                                Response.ErrorListener errorListener,
                                Uri uri) {
        requestQueue.add(new CachingStringRequest(
                Request.Method.GET,
                uri.toString(),
                responseListener,
                errorListener));
    }

    private void doCachedQuery(Response.Listener<String> responseListener,
                               Response.ErrorListener errorListener,
                               Uri uri) {
        if (!isResponseInCache(uri)) {
            errorListener.onErrorResponse(new NoConnectionError());
            return;
        }

        String cachedResponse = getResponseFromCache(uri);
        responseListener.onResponse(cachedResponse);
        errorListener.onErrorResponse(new NoConnectionError());
    }


    private boolean isNetworkAvailable() {
        NetworkInfo activeNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }

    private boolean isResponseInCache(Uri uri) {
        Cache cache = requestQueue.getCache();
        return cache.get(uri.toString()) != null;
    }

    private String getResponseFromCache(Uri uri) {
        Cache cache = requestQueue.getCache();
        Cache.Entry entry = cache.get(uri.toString());
        return new String(entry.data);
    }


    private Uri buildTeamsUri() {
        return Constants.BASE_URI.buildUpon().appendPath(Constants.API_PATH_API).appendPath(Constants.API_PATH_V1).appendPath(Constants.API_PATH_TEAMS).build();
    }

    private Uri buildPlayersUri(String playersQuery) {
        return Uri.parse(Constants.BASE_URI+playersQuery+File.separator+Constants.API_PATH_PLAYERS);
    }

    private Uri buildPlayerDetailsUri(String playerDetailQuery) {
        return  Uri.parse(Constants.BASE_URI+playerDetailQuery);
    }



}
