package com.mporject.interns.beatna;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.List;

public class AlbumAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> tracks;
    int resources;

    public AlbumAdapter(@NonNull Context context, int resource, @NonNull List<String> objects)
    {
        super(context, resource, objects);
        this.context=context;
        this.tracks = objects;
        this.resources=resource;
    }

    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView= inflater.inflate(resources, null);
        TextView tvAlbumName= (TextView) convertView.findViewById(R.id.txtAlbum);
        tvAlbumName.setText(tracks.get(position).toString());
        return convertView;
    }
}
