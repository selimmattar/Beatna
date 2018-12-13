package com.mporject.interns.beatna;

import android.content.Context;
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

public class FollowersAdapter extends ArrayAdapter<String> {
    Context context;
    List<String> abonnes;
    int resources;
    public FollowersAdapter(@NonNull Context context, int resource, @NonNull List<String> objects)
    {
        super(context, resource, objects);
        this.context=context;
        this.abonnes = objects;
        this.resources=resource;
    }


    @NonNull
    @Override
    public View getView(final int position, @Nullable View convertView, @NonNull ViewGroup parent)
    {
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView= inflater.inflate(resources, null);
        TextView txtUsername= (TextView) convertView.findViewById(R.id.txtFollower);
        txtUsername.setText(abonnes.get(position));

        return convertView;
    }
}
