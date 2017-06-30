package com.example.aksh.toptens;

import android.app.LoaderManager;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;


import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity {
    private TrackAdapter adapter;



    private static final String Api="http://www.billboard.com/rss/charts/hot-100";
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ArrayList<Track> track=new ArrayList<Track>();
        adapter=new TrackAdapter(this,R.layout.activity_main,track);
        ListView listView = (ListView) findViewById(R.id.list);

        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int position, long l) {

                Track current=(Track)adapterView.getItemAtPosition(position);
                String search=current.getTitle()+" by "+current.getArtist();
                String search_parameter="+";
                int j;
                for(j=0;j<search.length();j++)
                {
                    if(search.charAt(j)==' ')
                        break;

                }

                for(int i=j+1;i<search.length();i++)
                {
                    if(search.charAt(i)==' ')
                        search_parameter+='+';
                    else
                        search_parameter+=search.charAt(i);
                }
                search_parameter+='+';
                Log.v("search_para",search_parameter);
                Play_Activity send=new Play_Activity();
                String youtube_api="https://www.googleapis.com/youtube/v3/search?part=snippet&q="+search_parameter+"&key=AIzaSyAtdhAC_nNYYQye4_zrA6Yifwd0rZgsxrE";
//                send.recievekey(youtube_api);
                Log.v("Current API is",youtube_api);
                Log.v("Current song is",current.getTitle());
    // SENDING API TO DIFFERENT ACTIVITY BY ADDING A YOUTUBE KEY PARAMETER IN INTENT
                Intent i=new Intent(getApplicationContext(),Play_Activity.class);
                i.putExtra("youtube",youtube_api);
                startActivity(i);

            }
        });
        DownloadXmlTask task = new DownloadXmlTask();
        task.execute(Api);
    }



    public class DownloadXmlTask extends AsyncTask<String,Void,List<Track>> {

        @Override
        protected List<Track> doInBackground(String... url) {
            List<Track> result= new ArrayList<>();
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
            Log.v("On Post Execute","I am in On Post Execute method");
            Log.v("RESULT",result.get(0).getArtist());
            if(result.isEmpty())
                Log.v("message","empty");
        }

    }



}
