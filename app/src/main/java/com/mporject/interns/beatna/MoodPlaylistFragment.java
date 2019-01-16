package com.mporject.interns.beatna;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.functions.Consumer;
import io.reactivex.schedulers.Schedulers;

public class MoodPlaylistFragment extends Fragment {

    private NodeJS myAPI;
    private CompositeDisposable compositeDisposable= new CompositeDisposable();

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View myView=inflater.inflate(R.layout.album_interface,container,false);
        myAPI=MainActivity.Companion.getRetrofit().create(NodeJS.class);
        final ListView listALbum=myView.findViewById(R.id.listTracks);
        final ArrayList<String> album=new ArrayList<>();
        ImageView imgPlaylist= myView.findViewById(R.id.imgAlbum);
        TextView txtPlaylistName= myView.findViewById(R.id.txtAlbumName);
        ImageButton imgBack=myView.findViewById(R.id.imgBtnBackPlaylist);

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //PlaylistFragment pf=new PlaylistFragment();
                getFragmentManager().popBackStackImmediate();
            }
        });

        if(getArguments().getString("mood")=="Chill")
        imgPlaylist.setImageResource(R.drawable.relax);
        else if(getArguments().getString("mood")=="Sad")
            imgPlaylist.setImageResource(R.drawable.sad);
        else if(getArguments().getString("mood")=="Happy")
            imgPlaylist.setImageResource(R.drawable.happy);
        else if(getArguments().getString("mood")=="Studying")
            imgPlaylist.setImageResource(R.drawable.studying);
        else if(getArguments().getString("mood")=="Gaming")
            imgPlaylist.setImageResource(R.drawable.gaming);
        else if(getArguments().getString("mood")=="Love")
            imgPlaylist.setImageResource(R.drawable.love);
        else if(getArguments().getString("mood")=="Motivational")
            imgPlaylist.setImageResource(R.drawable.motivation);
        else if(getArguments().getString("mood")=="Sleepy")
            imgPlaylist.setImageResource(R.drawable.sleepy);
        txtPlaylistName.setText(getArguments().getString("mood"));

        compositeDisposable.add(myAPI.getMoodPlaylist(getArguments().getString("mood"))
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Consumer<String>() {
                    @Override
                    public void accept(String s) throws JSONException {
                        final JSONArray posts_data = new JSONArray(s);
                        final int n = posts_data.length();
                        for (int i = 0; i < n; ++i)
                        {
                            final JSONObject songs = posts_data.getJSONObject(i);
                            album.add(songs.getString("title"));
                            AlbumAdapter adapter = new AlbumAdapter(getContext(), R.layout.list_album, album);
                            listALbum.setAdapter(adapter);

                        }
                    }

                })
        );


        return myView;
    }


}
