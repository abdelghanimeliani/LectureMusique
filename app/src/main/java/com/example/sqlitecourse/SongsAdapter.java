package com.example.sqlitecourse;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.List;

public class SongsAdapter extends ArrayAdapter<Song> {
    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        convertView= LayoutInflater.from(parent.getContext()).inflate(R.layout.item_song,null);
        TextView title = convertView.findViewById(R.id.tvTitle);
        TextView artist = convertView.findViewById(R.id.tvArtiste);

        Song song = getItem(position);
        title.setText(song.getTitle());
        artist.setText(song.getArtist());
        return convertView;
    }

    public SongsAdapter(@NonNull Context context, int resource, @NonNull List<Song> objects) {
        super(context, resource, objects);
    }


}
