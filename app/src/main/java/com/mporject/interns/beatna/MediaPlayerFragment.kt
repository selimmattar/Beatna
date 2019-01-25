package com.mporject.interns.beatna

import android.media.MediaPlayer
import android.os.Bundle
import android.os.Handler
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.io.IOException
import android.widget.*
import com.marcinmoskala.arcseekbar.ArcSeekBar
import com.marcinmoskala.arcseekbar.ProgressListener


class MediaPlayerFragment : Fragment() {
     val playlist= ArrayList<Song>()
    var currentIndex:Int=0
    var album_title:String?=""
    var mp= MediaPlayer()
   var  mpIsEmpty=false
    var mp_length=mp.currentPosition
    var handler = Handler()
    var mySeekBar: ArcSeekBar? = null
      lateinit var runnable:Runnable


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {

        val song_moods=arguments!!.getStringArrayList("song_moods")
        val song_categories=arguments!!.getStringArrayList("song_categories")
        val song_ids=arguments!!.getIntegerArrayList("song_ids")
        val song_titles=arguments!!.getStringArrayList("song_titles")
        val artist_uids=arguments!!.getStringArrayList("artist_uids")
        val artist_names=arguments!!.getStringArrayList("artist_names")
        for(i in 0 until song_ids.size)
            playlist.add(Song(song_ids.get(i),song_titles.get(i),User(artist_uids.get(i),artist_names.get(i),"",1),song_moods.get(i),song_categories.get(i)))
        currentIndex=arguments!!.getInt("currentIndex")
        album_title=arguments!!.getString("album_title")
        runnable = Runnable {
            mySeekBar?.progress=mp.currentPosition
            handler.postDelayed(runnable,1000)
        }
        return inflater.inflate(R.layout.media_player,container,false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        val myView=view
        myView.findViewById<TextView>(R.id.songname_mp_tv).text=playlist.get(currentIndex).title
        myView.findViewById<TextView>(R.id.artistname_mp_tv).text=playlist.get(currentIndex).artist.name
        mp.setDataSource("http://10.0.2.2/Server/"+playlist.get(currentIndex).artist.name+"/"+
                album_title+"/"+playlist.get(currentIndex).title+".mp3")
        mp.prepare()
        mp.start()
        handler.postDelayed(runnable,1000)
         mySeekBar=myView.findViewById(R.id.seekArc)
     
        mySeekBar!!.maxProgress=mp.duration

      mySeekBar!!.onStopTrackingTouch= ProgressListener {
          mp.seekTo(it)
      }

        mp.setOnCompletionListener {
            if(currentIndex==playlist.size-1)
                currentIndex=0
            else currentIndex++
            mp.stop()
            mp.reset()
            myView.findViewById<TextView>(R.id.songname_mp_tv).text=playlist.get(currentIndex).title
            myView.findViewById<TextView>(R.id.artistname_mp_tv).text=playlist.get(currentIndex).artist.name
            playSong()
        }

        val PlayPause_btn=myView.findViewById<ImageButton>(R.id.play_imgb)
        PlayPause_btn.setOnClickListener{
            if(!mp.isPlaying){

                PlayPause_btn.setBackgroundResource(R.drawable.pause)
                if(mpIsEmpty){
                    try {
                        playSong()

                    } catch (e: IOException) {
                        e.printStackTrace()
                    }
                }else{
                    handler.postDelayed(runnable,1000)
                    mp.start()
                }
            }else {
                PlayPause_btn.setBackgroundResource(R.drawable.play)
                mp.pause()
                mp_length=mp.currentPosition
                handler.removeCallbacks(runnable)
            }


        }
        val forward_btn=myView.findViewById<ImageButton>(R.id.forward_imgb)
        forward_btn.setOnClickListener{
            if(currentIndex==playlist.size-1)
            currentIndex=0
            else currentIndex++
            mp.stop()
            mp.reset()
            mpIsEmpty=true
            handler.removeCallbacks(runnable)
            PlayPause_btn.setBackgroundResource(R.drawable.play)
            myView.findViewById<TextView>(R.id.songname_mp_tv).text=playlist.get(currentIndex).title
            myView.findViewById<TextView>(R.id.artistname_mp_tv).text=playlist.get(currentIndex).artist.name
        }
        val backward_btn=myView.findViewById<ImageButton>(R.id.back_imgb)
        backward_btn.setOnClickListener {

            if(currentIndex==0)
            currentIndex=playlist.size-1
            else currentIndex--
            mp.stop()
            mp.reset()
            mpIsEmpty=true
            PlayPause_btn.setBackgroundResource(R.drawable.play)
            myView.findViewById<TextView>(R.id.songname_mp_tv).text=playlist.get(currentIndex).title
            myView.findViewById<TextView>(R.id.artistname_mp_tv).text=playlist.get(currentIndex).artist.name
        }
    }
    fun playSong(){
        mp.setDataSource("http://10.0.2.2/Server/"+playlist.get(currentIndex).artist.name+"/"+
                album_title+"/"+playlist.get(currentIndex).title+".mp3")
        mp.prepareAsync()
        mp.setOnPreparedListener {
            mp.seekTo(mp_length)
            it.start()
            mySeekBar!!.maxProgress=mp.duration
            mpIsEmpty=false
        }
        runnable = Runnable {
            mySeekBar?.progress=mp.currentPosition
            handler.postDelayed(runnable,1000)
        }

    }
}