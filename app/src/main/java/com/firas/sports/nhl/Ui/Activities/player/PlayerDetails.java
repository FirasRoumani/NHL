package com.firas.sports.nhl.Ui.Activities.player;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.blongho.country_data.World;
import com.firas.sports.nhl.Application.NhlApp;
import com.firas.sports.nhl.ModelLayer.Models.Player;
import com.firas.sports.nhl.ModelLayer.Network.NetworkErrorDescriptor;
import com.firas.sports.nhl.R;

import javax.inject.Inject;

public class PlayerDetails extends AppCompatActivity implements PlayerPresenter.View  {

    @Inject
    PlayerPresenter mPresenter;

    ProgressBar mProgressBar;
    TextView player_nationality;
    ImageView player_country_flag;
    String player_link;

    public static final String BUNDLE_KEY_PLAYER = "BUNDLE_KEY_PLAYER";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_player_details);

        ((NhlApp)getApplication()).getAppComponent().inject(this);
        mProgressBar=findViewById(R.id.progress_bar);
        player_country_flag=findViewById(R.id.player_country_flag);
        player_nationality=findViewById(R.id.tv_player_nationality);

        Bundle extras=getIntent().getExtras();
        if(extras!=null)
            player_link=extras.getString(BUNDLE_KEY_PLAYER,"");

        mPresenter.setView(this);

        if(player_link!=null && !player_link.isEmpty())
            mPresenter.getPlayer(player_link);

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
        String message = NetworkErrorDescriptor.getDescription(this, exception);
        Toast.makeText(this, message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void showPlayer(Player player) {
        if(player!=null) {
            getSupportActionBar().setTitle(player.getName());
            player_nationality.setText(getString(R.string.nationality)+" "+player.getNationality());
            player_country_flag.setImageResource(World.getFlagOf(player.getNationality()));
        }
    }
}
