package com.example.aksh.toptens;

/**
 * Created by aksh on 21/2/17.
 */

public class Track {
    private String title;
    private String Artist;
    private String Current_ranking;
    private String Previous_ranking;
    private String url;

    public Track(String title, String artist, String current_ranking, String previous_ranking) {
        this.title = title;
        Artist = artist;
        Current_ranking = current_ranking;
        Previous_ranking = previous_ranking;
    }

    public String getTitle() {
        return title;
    }

    public String getArtist() {
        return Artist;
    }

    public String getCurrent_ranking() {
        return Current_ranking;
    }

    public String getPrevious_ranking() {
        return Previous_ranking;
    }

}
