package com.example.aksh.toptens;

import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.DocumentsContract;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.io.IOException;
import java.util.List;

import static android.R.attr.banner;
import static android.R.attr.configChanges;
import static com.example.aksh.toptens.R.id.artist;
import static com.example.aksh.toptens.R.id.song;


public class Play_Activity extends YouTubeBaseActivity implements LoaderCallbacks<String> {
    public static final String LOG_TAG = Play_Activity.class.getName();
    private String youtube_song;
    private String play;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer.OnInitializedListener onInitializedListener;
    TextView textView;
    String newSong="",newArtist="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_play_);
        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            String song,artist;
             youtube_song = extras.getString("youtube");
            song=extras.getString("song");
            artist=extras.getString("artist");
            artist=artist.toLowerCase();
            int ind=-1;
            ind=artist.indexOf("the");
            if(ind==0)
            {
                artist=artist.replaceFirst("the","");
            }
            artist=artist.split("featuring",2)[0];
            artist=artist.split(",",2)[0];
            artist=artist.split("&",2)[0];
            Log.v("artist",artist);
            song=song.toLowerCase();
            song=song.split(" ",2)[1];
            song=song.replaceAll("[^A-Za-z0-9]+","");
            artist=artist.replaceAll("[^A-Za-z0-9]+","");
            newSong=song;
            newArtist=artist;
//            for(int i=0;i<song.length();i++)
//            {
//                if(song.charAt(i)>='a'&&song.charAt(i)<='z')
//                    newSong+=song.charAt(i);
//                else
//                    continue;
//
//
//            }
//            for(int i=0;i<artist.length();i++)
//            {
//                if(artist.charAt(i)>='a'&&artist.charAt(i)<='z')
//                    newArtist+=artist.charAt(i);
//                else
//                    continue;
//
//            }


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
        Button button=(Button)findViewById(R.id.lyrButton);
        textView=(TextView)findViewById(R.id.lyricsbox);
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LyricsCrawler lyr=new LyricsCrawler();
                lyr.execute();
            }
        });
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

    public class LyricsCrawler extends AsyncTask<Void,Void,Void>{
        String str;

        @Override
        protected Void doInBackground(Void... params) {
            try {
                Document doc= Jsoup.connect("http://www.azlyrics.com/lyrics/"+newArtist+"/"+newSong+".html").get();
                str=doc.html();
                String up_partition = "<!-- Usage of azlyrics.com content by any third-party lyrics provider is prohibited by our licensing agreement. Sorry about that. -->";
                String down_partition = "<!-- MxM banner -->";
                str=str.split(up_partition)[1];
                str=str.split(down_partition)[0];
                str = str.replace("<br>","").replace("</br>","").replace("</div>","").replace("<i>","").replace("</i>","").trim();


            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }
        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            textView.setText(str);
            textView.setMovementMethod(new ScrollingMovementMethod());

        }
    }


}

