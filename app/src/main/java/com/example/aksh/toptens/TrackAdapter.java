package com.example.aksh.toptens;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

/**
 * Created by aksh on 21/2/17.
 */

public class TrackAdapter extends ArrayAdapter<Track> {
    public TrackAdapter(Context context, int resource, List<Track> objects) {
        super(context,0, objects);
    }

    @NonNull
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View listItemView = convertView;
        if (listItemView == null) {
            listItemView = LayoutInflater.from(getContext()).inflate(
                    R.layout.list_view, parent, false);
        }
        Track currentTrack=getItem(position);
        TextView songTextView=(TextView)listItemView.findViewById(R.id.song);
        songTextView.setText(currentTrack.getTitle());
        TextView artistTextView=(TextView)listItemView.findViewById(R.id.artist);
        artistTextView.setText(currentTrack.getArtist());
        TextView previousTextView=(TextView)listItemView.findViewById(R.id.previous);
        previousTextView.setText(currentTrack.getPrevious_ranking());
       TextView currentTextView=(TextView)listItemView.findViewById(R.id.current);
        currentTextView.setText(currentTrack.getCurrent_ranking());
         return listItemView;

    }

}
