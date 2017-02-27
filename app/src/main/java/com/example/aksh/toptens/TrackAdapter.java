package com.example.aksh.toptens;

import android.content.Context;
import android.graphics.drawable.GradientDrawable;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

import static com.example.aksh.toptens.R.id.current;
import static com.example.aksh.toptens.R.id.previous;

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
        TextView previousTextView=(TextView)listItemView.findViewById(previous);
        previousTextView.setText(currentTrack.getPrevious_ranking());
       TextView currentTextView=(TextView)listItemView.findViewById(current);
        currentTextView.setText(currentTrack.getCurrent_ranking());
        GradientDrawable current_Circle=(GradientDrawable)currentTextView.getBackground();
        int Color = getColor(currentTrack.getPrevious_ranking(),currentTrack.getCurrent_ranking());
        current_Circle.setColor(Color);
        GradientDrawable Prev_Circle=(GradientDrawable)previousTextView.getBackground();
        Prev_Circle.setColor(ContextCompat.getColor(getContext(),R.color.colorPrimary));
        return listItemView;
    }
    private int getColor(String previous,String current) {
        int ColorResourceId,p,c;
        p=Integer.parseInt(previous);
        c=Integer.parseInt(current);
        if(p<c&&p!=0)
            ColorResourceId=R.color.down;
        else if(c==p||p==0)
            ColorResourceId=R.color.colorPrimary;
        else
            ColorResourceId=R.color.up;
        return ContextCompat.getColor(getContext(),ColorResourceId);
    }

}

