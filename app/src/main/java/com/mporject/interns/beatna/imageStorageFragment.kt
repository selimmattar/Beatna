package com.mporject.interns.beatna

import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.MediaStore
import android.support.v4.app.ActivityCompat
import android.support.v4.app.Fragment
import android.support.v4.content.ContextCompat
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ListView
import android.widget.Toast
import java.util.ArrayList

class imageStorageFragment : Fragment() {
    val storagelist = ArrayList<FileToUpload>()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val myView = inflater.inflate(R.layout.storagefragment, container, false)
        return myView
    }
    fun getMusic(){
        val contentResolver =context!!.contentResolver
        val songUri= MediaStore.Images.Media.EXTERNAL_CONTENT_URI
        val  songCursor=contentResolver.query(songUri,null,null,null,null)
        if(songCursor!=null && songCursor.moveToFirst()){
            val songTitle=songCursor.getColumnIndex(MediaStore.Audio.Media.TITLE)
            val songPath=songCursor.getColumnIndex(MediaStore.Audio.Media.DATA)
            var fileToUpload:FileToUpload
            do {
                val currentTitle=songCursor.getString(songTitle)
                val currentPath=songCursor.getString(songPath)
                fileToUpload= FileToUpload(currentTitle,currentPath)
                storagelist.add(fileToUpload)
            }while (songCursor.moveToNext())
        }
    }
    fun doStuff(){
        val storage_lv=view?.findViewById<ListView>(R.id.storage_lv)
        val fileAdapter=ImageFileAdapter(context!!, R.id.storage_lv, storagelist)

        getMusic()
        fileAdapter.imgsf=this
        storage_lv!!.adapter=fileAdapter

    }
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if(requestCode==1){
            if(grantResults.size>0 && grantResults[0]== PackageManager.PERMISSION_GRANTED){
                if(ContextCompat.checkSelfPermission(context!!,android.Manifest.permission.READ_EXTERNAL_STORAGE)== PackageManager.PERMISSION_GRANTED)
                    Toast.makeText(context!!,"Permission Granted", Toast.LENGTH_SHORT).show()
                doStuff()
            }else {
                Toast.makeText(context!!,"No Permission Granted", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        if(ContextCompat.checkSelfPermission(context!!,android.Manifest.permission.READ_EXTERNAL_STORAGE)!= PackageManager.PERMISSION_GRANTED){
            if (ActivityCompat.shouldShowRequestPermissionRationale(this.activity!!,android.Manifest.permission.READ_EXTERNAL_STORAGE)){
                ActivityCompat.requestPermissions(this.activity!!, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1 )
            }else {
                ActivityCompat.requestPermissions(this.activity!!, arrayOf(android.Manifest.permission.READ_EXTERNAL_STORAGE),1 )
            }
        }else {

            doStuff()
        }
    }
}