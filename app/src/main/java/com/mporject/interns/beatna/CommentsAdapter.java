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

public class CommentsAdapter extends ArrayAdapter<Comments> {
    Context context;
    List<Comments> comments;
    int resources;

    public CommentsAdapter(@NonNull Context context, int resource,@NonNull List<Comments> objects) {
        super(context, resource, objects);
        this.context=context;
        this.comments = objects;
        this.resources=resource;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        LayoutInflater inflater=LayoutInflater.from(context);
        convertView= inflater.inflate(resources, null);
        TextView txtUserName= convertView.findViewById(R.id.txtUserCommentItem);
        TextView txtCreatedAt= convertView.findViewById(R.id.txtCreatedCommentItem);
        TextView txtCommentaire= convertView.findViewById(R.id.txtCommentaireCommentItem);

        txtUserName.setText(comments.get(position).getUserCmt());
        txtCreatedAt.setText(comments.get(position).getDateCmt());
        txtCommentaire.setText(comments.get(position).getCommentCmt());
        return convertView;
    }


}
