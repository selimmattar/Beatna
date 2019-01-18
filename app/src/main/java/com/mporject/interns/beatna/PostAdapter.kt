package com.mporject.interns.beatna

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.support.v7.app.AppCompatActivity
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.widget.*
import java.net.URL
import com.squareup.picasso.Picasso
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray


class PostAdapter(context: Context, resource: Int, objects: MutableList<Post>) : ArrayAdapter<Post>(context, resource, objects) {
    val myAPI = MainActivity.retrofit!!.create(NodeJS::class.java)
    val CD = CompositeDisposable()
    val myDb= DatabaseHelper(context)
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view :View?=null

        val inflater = LayoutInflater.from(context)

        view=inflater?.inflate(R.layout.newsfeed_item,parent,false)
        view?.findViewById<TextView>(R.id.username_tv)?.text=getItem(position).poster.name
        view?.findViewById<TextView>(R.id.songname_tv)?.text=getItem(position).song.title
       val profile_pic=view?.findViewById<ImageView>(R.id.profile_img)
        val song_pic=view?.findViewById<ImageView>(R.id.song_imgv)
        val imageUri = "http://10.0.2.2/Server/"+getItem(position).poster.name+"/photo.jpg"
        val SongimageUri = "http://10.0.2.2/Server/"+getItem(position).poster.name+"/Singles/albumPhoto.jpg"
        Picasso.with(context).load(imageUri).transform(CircleTransform()).into(profile_pic)
        Picasso.with(context).load(SongimageUri).resize(1200,500).centerCrop().into(song_pic)
        view?.findViewById<ImageButton>(R.id.playpost_imgb)!!.setOnClickListener{
        //getAlbumSongs(getItem(position).song.id,position)
            //addMoodCategoryLite(position)
            displayMoodCategoryLite()

        }
        profile_pic!!.setOnClickListener(View.OnClickListener {
            val profileFragment = AllProfileFragment()
            val bundle = Bundle()
            bundle.putString("uniqueId", getItem(position).poster.uid)
            bundle.putString("userName",getItem(position).poster.name)
            bundle.putString("userEmail",getItem(position).poster.email)

            profileFragment.arguments = bundle
            val manager = (context as AppCompatActivity).supportFragmentManager
            manager.beginTransaction().replace(R.id.fragment_container, profileFragment).addToBackStack(null).commit()
        })
        return view!!
    }
private fun  getAlbumSongs(id:Int,position:Int){
    val songs=ArrayList<Song>()
    var album:Album= Album(0,"",0,songs)
CD.add(myAPI.getAlbumSongsBySongId(id)
        .subscribeOn(Schedulers.io())
        .observeOn(AndroidSchedulers.mainThread())
        .subscribe{
            val songs_data = JSONArray(it)
            val n = songs_data.length()
            for (i in 0 until n) {
                val post =songs_data.getJSONObject(i)
               val artist = User(post.getString("unique_id"), post.getString("name"),"",1)
               val song = Song(post.getInt("song_id"), post.getString("song_title"), artist,post.getString("song_mood"),post.getString("song_category"))
                songs.add(song)

            }
             album=Album(songs_data.getJSONObject(0).getInt("album_id"),songs_data.getJSONObject(0).getString("album_title"),songs.size,songs)
            val bundle=Bundle()
            bundle.putString("song_name",getItem(position).song.title)

            val song_titles=ArrayList<String>()
            val song_ids=ArrayList<Int>()
            val artist_uids=ArrayList<String>()
            val artist_names=ArrayList<String>()
            val song_moods=ArrayList<String>()
            val song_categories=ArrayList<String>()
            album.songs.forEach{
                song_titles.add(it.title)
                song_ids.add(it.id)
                artist_uids.add(it.artist.uid)
                artist_names.add(it.artist.name)
                song_moods.add(it.mood)
                song_categories.add(it.category)
            }
            bundle.putStringArrayList("song_categories",song_categories)
            bundle.putStringArrayList("song_moods",song_moods)
            bundle.putStringArrayList("song_titles",song_titles)
            bundle.putIntegerArrayList("song_ids",song_ids)
            bundle.putStringArrayList("artist_uids",artist_uids)
            bundle.putStringArrayList("artist_names",artist_names)
            bundle.putString("album_title",album.title)
            bundle.putInt("album_id",album.id)
            bundle.putInt("currentIndex",song_ids.indexOf(getItem(position).song.id))
            val MPF=MediaPlayerFragment()
            MPF.arguments=bundle
            val manager = (context as AppCompatActivity).supportFragmentManager

            manager.beginTransaction().replace(R.id.fragment_container,MPF!!).addToBackStack(null).commit()
        })
}

    private fun  addMoodCategoryLite(position:Int){
        val isInserted=myDb.insertData(getItem(position).song.mood,getItem(position).song.category)
            if(isInserted==true)
                println("true")
            else
                println("false")

    }
    private fun displayMoodCategoryLite()
    {
        val res = myDb.bestMood
        if(res.count==0)
            println("no data")
        val buffer=StringBuffer()
        while(res.moveToNext())
        {
            buffer.append("\n mood:"+res.getString(0)+"\n")
            buffer.append("\n occ:"+res.getString(1)+"\n")
        }
        println("data:"+buffer.toString())
    }
}