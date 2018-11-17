package com.mporject.interns.beatna;


import android.media.MediaPlayer;

import android.os.Bundle;

import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.gauravk.audiovisualizer.visualizer.BlastVisualizer;

import java.io.IOException;


public class HomeFragment extends Fragment {
MediaPlayer mp;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View myView=inflater.inflate(R.layout.fragment_home,container,false);
         mp =new MediaPlayer();
        //get reference to visualizer
       BlastVisualizer mVisualizer = myView.findViewById(R.id.blast);

        //TODO: init MediaPlayer and play the audio

        //get the AudioSessionId from your MediaPlayer and pass it to the visualizer
        int audioSessionId = mp.getAudioSessionId();
        try {
            mp.setDataSource("http://10.0.2.2/Server/ArtistNu1/AlbumNu1/Bratia Stereo - Ayayay (ft. Tony Tonite).mp3");
            mp.prepare();
            mp.start();
            Toast.makeText(getActivity(),""+audioSessionId,Toast.LENGTH_SHORT).show();
            if (audioSessionId != -1)
                mVisualizer.setAudioSessionId(audioSessionId);

        } catch (IOException e) {
            e.printStackTrace();
        }



// set custom color to the line.



        return myView;
    }

    @Override
    public void onDestroyView() {
      mp.stop();
        super.onDestroyView();
    }
}
