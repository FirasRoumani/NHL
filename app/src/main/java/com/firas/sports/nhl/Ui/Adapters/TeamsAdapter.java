package com.firas.sports.nhl.Ui.Adapters;

import android.app.Activity;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firas.sports.nhl.Listeners.ItemClickListener;
import com.firas.sports.nhl.ModelLayer.Models.Team;
import com.firas.sports.nhl.R;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYou;
import com.github.twocoffeesoneteam.glidetovectoryou.GlideToVectorYouListener;
import java.util.ArrayList;
import java.util.List;

public class TeamsAdapter extends RecyclerView.Adapter<TeamsAdapter.MyViewHolder> {


    private List<Team> teamsList;
    private Activity activity;
    private int displayLayout;
    private ItemClickListener mItemClickListener;

    public TeamsAdapter(Activity activity,int displayLayout) {
        this.teamsList          = new ArrayList<Team>();
        this.activity           =activity;
        this.displayLayout      =displayLayout;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new MyViewHolder(LayoutInflater.from(parent.getContext()).inflate(displayLayout, parent, false),
                mItemClickListener);

    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        try {
            final Team teamObject = teamsList.get(position);

            if(teamObject!=null) {
                holder.team_name.setText(teamObject.getTeam_name());

                if (teamObject.getTeam_logo_url() != null && teamObject.getTeam_logo_url().length() > 0){
                    GlideToVectorYou
                            .init()
                            .with(activity)
                            .withListener(new GlideToVectorYouListener() {
                                @Override
                                public void onLoadFailed() {
                                    Log.e("Teams Adapter","Load failed");

                                }

                                @Override
                                public void onResourceReady() {
                                    Log.d("Teams Adapter","image Loaded");
                                }
                            })
                            .setPlaceHolder(R.drawable.ic_file_download, R.drawable.ic_error)
                            .load(Uri.parse(teamObject.getTeam_logo_url()), holder.team_logo);
                }else
                    holder.team_logo.setImageResource(R.drawable.ic_error);


                holder.team_name.setTag(teamObject);
            }
        } catch (Exception ex) {
            Log.e("TeamsAdapter", "onBindViewHolder:  ", ex);
        }



    }

    class MyViewHolder extends RecyclerView.ViewHolder{
        private TextView team_name ;
        private ImageView team_logo;



        private MyViewHolder(@NonNull final View view, ItemClickListener listener) {
            super(view);
            team_name    = view.findViewById(R.id.team_name);
            team_logo = view.findViewById(R.id.team_logo);
            itemView.setOnClickListener(v -> {
                if (listener != null) {
                    int pos = getAdapterPosition();
                    if (pos != RecyclerView.NO_POSITION) {
                        listener.onItemClick(pos);
                    }
                }
            });
        }

    }

    @Override
    public int getItemCount() {
        return teamsList.size();
    }

    public List<Team> getTeamsList() {
        return teamsList;
    }

    public void setTeamsList(List<Team> teamsList) {
        this.teamsList = teamsList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.mItemClickListener = listener;
    }

}
