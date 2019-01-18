package com.mporject.interns.beatna;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
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
        TextView tvAlbumName=convertView.findViewById(R.id.txtAlbum);
        TextView tvOrder= convertView.findViewById(R.id.txtOrder);
        TextView tvMinutes= convertView.findViewById(R.id.txtMinutes);

        tvAlbumName.setText(tracks.get(position));
        tvOrder.setText(""+(position+1));
        tvMinutes.setText("2:16");
       /* tvAlbumName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            SongFragment SF=new SongFragment();
                Bundle bundle=new Bundle();
                bundle.putString("song_name",tracks.get(position));
                SF.setArguments(bundle);
                FragmentManager manager=((AppCompatActivity) context).getSupportFragmentManager();
                manager.beginTransaction().replace(R.id.fragment_container,SF).addToBackStack(null).commit();
            }
        });
        */
        return convertView;
    }
}
