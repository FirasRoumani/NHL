package com.firas.sports.nhl.ModelLayer.Models;

import android.os.Parcel;
import android.os.Parcelable;

public class Player implements Parcelable {

    private int player_id;
    private String name;
    private int player_num;
    private String position;
    private String nationality;
    private String playerLink;


    public Player(int player_id, String name, int player_num, String position, String nationality,String playerLink) {
        this.player_id = player_id;
        this.name = name;
        this.player_num = player_num;
        this.position = position;
        this.nationality = nationality;
        this.playerLink=playerLink;
    }

    public int getPlayer_id() {
        return player_id;
    }

    public void setPlayer_id(int player_id) {
        this.player_id = player_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPlayer_num() {
        return player_num;
    }

    public void setPlayer_num(int player_num) {
        this.player_num = player_num;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getNationality() {
        return nationality;
    }

    public void setNationality(String nationality) {
        this.nationality = nationality;
    }

    public String getPlayerLink() {
        return playerLink;
    }

    public void setPlayerLink(String playerLink) {
        this.playerLink = playerLink;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(getPlayer_id());
        dest.writeString(getName());
        dest.writeInt(getPlayer_num());
        dest.writeString(getPosition());
        dest.writeString(getNationality());
        dest.writeString(getPlayerLink());
    }

    protected Player(Parcel in) {
        this.player_id=in.readInt();
        this.name = in.readString();
        this.player_num = in.readInt();
        this.position=in.readString();
        this.nationality = in.readString();
        this.playerLink=in.readString();
    }


    public static final Creator<Player> CREATOR = new Creator<Player>() {
        @Override
        public Player createFromParcel(Parcel in) {
            return new Player(in);
        }

        @Override
        public Player[] newArray(int size) {
            return new Player[size];
        }
    };
}
