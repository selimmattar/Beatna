package com.mporject.interns.beatna

import android.media.MediaPlayer
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import java.io.IOException


class SongFragment : Fragment() {
    val mp=MediaPlayer()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.media_player,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myView=view
        myView.findViewById<TextView>(R.id.songname_mp_tv).text=arguments!!.getString("song_name")
       val song_img=myView.findViewById<ImageView>(R.id.song_mp_iv)
        val PlayPause_btn=myView.findViewById<Button>(R.id.playpausebtn)
        PlayPause_btn.setOnClickListener{
            try {
                mp.setDataSource("http://10.0.2.2/Server/selim/AlbumNu1/Bratia Stereo - Ayayay (ft. Tony Tonite).mp3")
                mp.prepare()
                mp.start()

            } catch (e: IOException) {
                e.printStackTrace()
            }

        }
    }
}