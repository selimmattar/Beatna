package com.mporject.interns.beatna

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.TextView

class FileAdapter(context: Context, resource: Int, objects: MutableList<FileToUpload>) : ArrayAdapter<FileToUpload>(context, resource, objects) {

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        var view :View?=null

        val inflater = LayoutInflater.from(context)

        view=inflater?.inflate(R.layout.storage_item,parent,false)
        view?.findViewById<TextView>(R.id.storageItemName_tv)?.text=getItem(position).name
        view?.findViewById<Button>(R.id.addFile_btn)?.setOnClickListener{
            val bundle= Bundle()
            bundle.putString("songName",getItem(position).name)
            bundle.putString("songUri",getItem(position).path)

            val manager = (context as AppCompatActivity).supportFragmentManager
            val sf=imageStorageFragment()
            sf.arguments=bundle
            manager.beginTransaction().replace(R.id.fragment_container,sf!!).addToBackStack(null).commit()
        }
        return view!!
    }
}