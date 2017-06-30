package com.example.aksh.toptens;

import android.text.TextUtils;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.Charset;
import static com.example.aksh.toptens.Play_Activity.LOG_TAG;

/**
 * Created by aksh on 30/6/17..
 */

public class JsonQuery {
    
    private static URL createUrl(String stringUrl) {
        URL url = null;
        try {
            url = new URL(stringUrl);
        } catch (MalformedURLException e) {

            Log.e(LOG_TAG, "Problem building the url", e);
        }
        return url;
    }

   
    private static String makeHttpRequest(URL url) throws IOException {
        String jsonResponse = "";

        // If the URL is null, then return early.
        if (url == null) {
            return jsonResponse;
        }

        HttpURLConnection urlConnection = null;
        InputStream inputStream = null;
        try {
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setReadTimeout(10000 /* milliseconds */);
            urlConnection.setConnectTimeout(15000 /* milliseconds */);
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            // If the request was successful (response code 200),
            // then read the input stream and parse the response.
            if (urlConnection.getResponseCode() == 200) {
                inputStream = urlConnection.getInputStream();
                jsonResponse = readFromStream(inputStream);
            } else {
                Log.e(LOG_TAG, "Error response code: " + urlConnection.getResponseCode());
            }
        } catch (IOException e) {
            Log.e("LOG_TAG", "Problem retrieving the Youtube JSON", e);
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (inputStream != null) {
               
                inputStream.close();
            }
        }
        Log.v("json response",jsonResponse);
        return jsonResponse;
    }
    
    private static String readFromStream(InputStream inputStream) throws IOException {
        StringBuilder output = new StringBuilder();
        if (inputStream != null) {
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, Charset.forName("UTF-8"));
            BufferedReader reader = new BufferedReader(inputStreamReader);
            String line = reader.readLine();
            while (line != null) {
                output.append(line);
                line = reader.readLine();
            }
        }
        return output.toString();
    }
   
    private static String extractFeatureFromJson(String youtubeJSON) {
        if (TextUtils.isEmpty(youtubeJSON)) {
            return null;
        }

        String video=null;

      
        try {

            // Create a JSONObject from the JSON response string
            JSONObject baseJsonResponse = new JSONObject(youtubeJSON);

            JSONArray youtubeArray = baseJsonResponse.getJSONArray("items");

         


               
                JSONObject currentSong = youtubeArray.getJSONObject(0);

             
                JSONObject properties = currentSong.getJSONObject("id");

                 video=properties.getString("videoId");
                Log.v("VIDEO",video);

                Log.v("The video key is",video);
                return video;


        } catch (JSONException e) {

            Log.e("QueryUtils", "Problem parsing the Youtube JSON ", e);
        }

        // Return the list of YoutubeResponse
        return video;
    }
   
    public static String fetchYoutubeKey(String requestUrl) {
        // Create URL object
        URL url = createUrl(requestUrl);
        Log.v("Url",url.toString());

        // Perform HTTP request to the URL and receive a JSON response back
        String jsonResponse = null;
        try {
            jsonResponse = makeHttpRequest(url);
        } catch (IOException e) {
            Log.e(LOG_TAG, "Problem making the HTTP request.", e);
        }

        String YoutubeResponse = extractFeatureFromJson(jsonResponse);

        return YoutubeResponse;
    }
}
