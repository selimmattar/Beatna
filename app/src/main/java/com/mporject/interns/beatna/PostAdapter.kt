package com.mporject.interns.beatna

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.TextView
import android.support.v7.app.AppCompatActivity
import android.graphics.BitmapFactory
import android.graphics.Bitmap
import android.widget.ImageView
import java.net.URL
import com.squareup.picasso.Picasso




class PostAdapter(context: Context, resource: Int, objects: MutableList<Post>) : ArrayAdapter<Post>(context, resource, objects) {

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
        val bundle=Bundle()
            bundle.putString("song_name",getItem(position).song.title)

            val SF=SongFragment()
            SF.arguments=bundle
            val manager = (context as AppCompatActivity).supportFragmentManager

            manager.beginTransaction().replace(R.id.fragment_container,SF!!).addToBackStack(null).commit()
        }
        return view!!
    }


}