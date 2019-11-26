package com.firas.sports.nhl.ModelLayer.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Team implements Parcelable {

    private int team_id;
    private String team_name;
    private String team_logo_url;
    private String players_link;

    public Team(int team_id, String team_name, String team_logo_url,String players_link) {
        this.team_id = team_id;
        this.team_name = team_name;
        this.team_logo_url = team_logo_url;
        this.players_link=players_link;
    }

    public int getTeam_id() {
        return team_id;
    }

    public void setTeam_id(int team_id) {
        this.team_id = team_id;
    }

    public String getTeam_name() {
        return team_name;
    }

    public void setTeam_name(String team_name) {
        this.team_name = team_name;
    }

    public String getTeam_logo_url() {
        return team_logo_url;
    }

    public void setTeam_logo_url(String team_logo_url) {
        this.team_logo_url = team_logo_url;
    }


    public String getPlayers_link() {
        return players_link;
    }

    public void setPlayers_link(String players_link) {
        this.players_link = players_link;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {

        dest.writeInt(getTeam_id());
        dest.writeString(getTeam_name());
        dest.writeString(getTeam_logo_url());
        dest.writeString(getPlayers_link());

    }



    protected Team(Parcel in) {
        this.team_id=in.readInt();
        this.team_name = in.readString();
        this.team_logo_url = in.readString();
        this.players_link=in.readString();
    }

    public static final Creator<Team> CREATOR = new Creator<Team>() {
        @Override
        public Team createFromParcel(Parcel in) {
            return new Team(in);
        }

        @Override
        public Team[] newArray(int size) {
            return new Team[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }


}
