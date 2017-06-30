package com.example.aksh.toptens;

import android.content.Context;
//this import----->remember
import android.content.AsyncTaskLoader;
import android.util.Log;

/**
 * Created by aksh on 30/6/17.
 */

public class JsonLoader extends AsyncTaskLoader<String> {
    /** Query URL */
    private String mUrl;
    public JsonLoader(Context context,String url) {
        super(context);
        mUrl=url;
        Log.v("URl",mUrl);
    }

    @Override
    protected void onStartLoading() {
        forceLoad();
    }

    @Override
    public String loadInBackground() {

        if (mUrl == null) {
            return null;
        }
        String search_key = JsonQuery.fetchYoutubeKey(mUrl);
        Log.v("Search key",search_key);
        return search_key;
    }
}
