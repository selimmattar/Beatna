package com.mporject.interns.beatna

import android.content.Context
import android.os.Bundle
import android.support.v4.app.FragmentManager
import android.support.v7.widget.RecyclerView.ViewHolder
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import android.support.v7.app.AppCompatActivity



class PostAdapter(context: Context, resource: Int, objects: MutableList<Post>) : ArrayAdapter<Post>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view :View?=null

        val inflater = LayoutInflater.from(context)

        view=inflater?.inflate(R.layout.newsfeed_item,parent,false)



        view?.findViewById<TextView>(R.id.artistname_tv)?.text=getItem(position).username
        view?.findViewById<TextView>(R.id.songname_tv)?.text=getItem(position).songname
        view?.findViewById<ImageButton>(R.id.song_imgbtn)!!.setOnClickListener{
        val bundle=Bundle()
            bundle.putString("song_name",getItem(position).songname)

            val SF=SongFragment()
            SF.arguments=bundle
            val manager = (context as AppCompatActivity).supportFragmentManager

            manager.beginTransaction().replace(R.id.fragment_container,SF!!).addToBackStack(null).commit()
        }
        return view!!
    }


}