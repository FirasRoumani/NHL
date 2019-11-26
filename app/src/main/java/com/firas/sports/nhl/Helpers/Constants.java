package com.firas.sports.nhl.Helpers;

import android.net.Uri;

public class Constants {

    public static final String NHL_API_URL       = "https://statsapi.web.nhl.com";
    public static final String NHL_TEAM_LOGO_URL = "https://www-league.nhlstatic.com/images/logos/teams-current-primary-light/";
    public static final String NHL_TEAM_LOGO_EXT = ".svg";
    public static final String API_PATH_API      = "api";
    public static final String API_PATH_V1       = "v1";
    public static final String API_PATH_PLAYERS  = "roster";
    public static final String API_PATH_TEAMS    = "teams";

    public static final String JSON_KEY_TEAMS = "teams";
    public static final String JSON_KEY_PLAYERS = "roster";
    public static final String JSON_KEY_PEOPLE = "people";


    public static final Uri BASE_URI = Uri.parse(NHL_API_URL).buildUpon().build();
}
