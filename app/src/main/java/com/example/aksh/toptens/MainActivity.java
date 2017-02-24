package com.example.aksh.toptens;

import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.widget.ListView;
import android.app.LoaderManager;
import android.app.LoaderManager.LoaderCallbacks;
import android.content.Loader;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static android.R.attr.data;
import static android.R.attr.entries;

public class MainActivity extends AppCompatActivity {
    private TrackAdapter adapter;
    private static final String Api="http://www.billboard.com/rss/charts/hot-100";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Track> track=new ArrayList<Track>();
 //       track.add(new Track("ed sheeran","shape of you","1","1"));
//        track.add(new Track("Ed Sheeran","Castle on Hill","2","5"));
//        track.add(new Track("zayn malik","i dont wanna live forever","3","6"));
//        track.add(new Track("chainsmokers","Closer","4","2"));
//        track.add(new Track("Weeknd","Starboy","5","2"));
//        track.add(new Track("Weeknd","Starboy","5","2"));
//        track.add(new Track("Weeknd","Starboy","5","2"));
//        track.add(new Track("Weeknd","Starboy","5","2"));
//        track.add(new Track("Weeknd","Starboy","5","2"));
//        track.add(new Track("Weeknd","Starboy","5","2"));
//        track.add(new Track("Weeknd","Starboy","5","2"));
//        track.add(new Track("Weeknd","Starboy","5","2"));
        adapter=new TrackAdapter(this,R.layout.activity_main,track);
        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
        DownloadXmlTask task = new DownloadXmlTask();
        task.execute(Api);
    }
    public class DownloadXmlTask extends AsyncTask<String,Void,List<Track>> {

        @Override
        protected List<Track> doInBackground(String... url) {
            List<Track> result= null;
            try {
                result = QueryUtils.fetchData(url[0]);
            } catch (XmlPullParserException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return  result;
        }
        @Override
        protected void onPostExecute(List<Track> result) {
            adapter.clear();
            if (result!= null && !result.isEmpty())
            adapter.addAll(result);
            if(result.isEmpty());
                Log.v("message","empty");
        }

    }


}
