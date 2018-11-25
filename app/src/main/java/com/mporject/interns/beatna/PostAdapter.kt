package com.mporject.interns.beatna

import android.content.Context
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView

class PostAdapter(context: Context, resource: Int, objects: MutableList<Post>) : ArrayAdapter<Post>(context, resource, objects) {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view :View?=null

        val inflater = LayoutInflater.from(context)

        view=inflater?.inflate(R.layout.newsfeed_item,parent,false)
        User.getUserByUid(getItem(position).artist)
        Song.getSongById(getItem(position).song)
        view?.findViewById<TextView>(R.id.artistname_tv)?.text=User.user.name
        view?.findViewById<TextView>(R.id.songname_tv)?.text=Song.song.title
        return view!!
    }

}