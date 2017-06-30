package com.example.aksh.toptens;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.Bundle;
import android.util.Log;
import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;


public class Play_Activity extends YouTubeBaseActivity implements LoaderCallbacks<String> {
    public static final String LOG_TAG = Play_Activity.class.getName();
    private String youtube_song;
    private String play;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
             youtube_song = extras.getString("youtube");
//get the value based on the key
        }
        youTubePlayerView=(YouTubePlayerView)findViewById(R.id.youtube_player);


        onInitializedListener=new YouTubePlayer.OnInitializedListener() {
            @Override
            public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
                youTubePlayer.loadVideo(play);

            }

            @Override
            public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

            }
        };
        LoaderManager loaderManager = getLoaderManager();
        loaderManager.initLoader(0, null,Play_Activity.this);


    }

    @Override
    public Loader<String> onCreateLoader(int id, Bundle args) {
        Log.v("Youtube song ","i am in on create loader");
        return new JsonLoader(this,youtube_song);
    }

    @Override
    public void onLoadFinished(Loader<String> loader, String data) {
        Log.v("THE SONG WE GOT",data);
        play=data;
        youTubePlayerView.initialize("AIzaSyAtdhAC_nNYYQye4_zrA6Yifwd0rZgsxrE",onInitializedListener);

    }

    @Override
    public void onLoaderReset(Loader<String> loader) {
        play=null;

    }

}

