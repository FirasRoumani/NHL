package com.firas.sports.nhl.ModelLayer.Network;

import com.android.volley.Response;
import com.firas.sports.nhl.ModelLayer.Models.Player;
import com.firas.sports.nhl.ModelLayer.Models.Team;

import org.json.JSONException;

import java.util.List;

public interface NhlApi {

    void GetTeams(Response.Listener<String> responseListener,
                      Response.ErrorListener errorListener);

    void GetPlayers(String teamUrl,
                      Response.Listener<String> responseListener,
                      Response.ErrorListener errorListener);

    void GetPlayerDetail(String playerDetailUrl,
                         Response.Listener<String> responseListener,
                         Response.ErrorListener errorListener);

    List<Team> parseTeamsResponse(String jsonString) throws JSONException;
    List<Player> parsePlayersResponse(String jsonArrayString) throws JSONException;
    Player parsePlayerDetailResponse(String jsonArrayString) throws JSONException;
}
