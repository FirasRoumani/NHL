package com.firas.sports.nhl.Ui.Adapters;

import android.app.Activity;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.firas.sports.nhl.Listeners.ItemClickListener;
import com.firas.sports.nhl.ModelLayer.Models.Player;
import com.firas.sports.nhl.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class PlayersAdapter extends RecyclerView.Adapter<PlayersAdapter.MyViewHolder> implements Filterable {


    private List<Player> playerList;
    private List<Player> playerListFiltered;
    private int displayLayout;
    private ItemClickListener mItemClickListener;

    public PlayersAdapter(int displayLayout) {
        this.playerList = new ArrayList<>();
        this.playerListFiltered= new ArrayList<>();
        this.displayLayout = displayLayout;

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
            final Player playerObject = playerListFiltered.get(position);

            if (playerObject != null) {
                holder.player_name.setText(playerObject.getName());
                holder.player_position.setText(playerObject.getPosition());
                holder.player_number.setText("#"+playerObject.getPlayer_num());
                holder.player_name.setTag(playerObject);
            }
        } catch (Exception ex) {
            Log.e("PlayersAdapter", "onBindViewHolder:  ", ex);
        }


    }




    class MyViewHolder extends RecyclerView.ViewHolder {
        private TextView player_name;
        private TextView player_position;
        private TextView player_number;


        private MyViewHolder(@NonNull final View view, ItemClickListener listener) {
            super(view);
            player_name = view.findViewById(R.id.player_name);
            player_position = view.findViewById(R.id.player_position);
            player_name = view.findViewById(R.id.player_name);
            player_number = view.findViewById(R.id.player_number);
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
        return playerListFiltered.size();
    }

    public List<Player> getPlayerList() {
        return playerListFiltered;
    }

    public void setPlayerList(List<Player> playerList) {

        this.playerList = playerList;
        this.playerListFiltered=playerList;
    }

    public void setOnItemClickListener(ItemClickListener listener) {
        this.mItemClickListener = listener;
    }


    @Override
    public Filter getFilter() {
        return new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence charSequence) {
                String charString = charSequence.toString();
                if (charString.isEmpty()) {
                    playerListFiltered = playerList;
                } else {
                    List<Player> filteredList = new ArrayList<>();
                    for (Player row : playerList) {

                        if (row.getPosition().toLowerCase().contains(charString.toLowerCase())) {
                            filteredList.add(row);
                        }
                    }

                    playerListFiltered = filteredList;
                }

                FilterResults filterResults = new FilterResults();
                filterResults.values = playerListFiltered;
                return filterResults;
            }

            @Override
            protected void publishResults(CharSequence charSequence, FilterResults filterResults) {
                try{
                    playerListFiltered = (ArrayList<Player>) filterResults.values;
                    notifyDataSetChanged();
                }catch (Exception ex){
                    Log.e("Exception", Objects.requireNonNull(ex.getMessage()));
                }

            }
        };
    }


    public void sortByNumber(){
            Collections.sort(playerListFiltered, (o1, o2) -> Integer.valueOf(o1.getPlayer_num()).compareTo( Integer.valueOf(o2.getPlayer_num())));
        notifyDataSetChanged();

    }

    public void sortByName(){
        Collections.sort(playerListFiltered, (o1, o2) -> o1.getName().compareTo( o2.getName()));
        notifyDataSetChanged();

    }



}

