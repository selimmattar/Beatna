package com.mporject.interns.beatna

import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject

class Song {
     var title: String? = ""
    companion object {
        val song =Song()
                fun getSongById(id:Int){

                    val myAPI : NodeJS
                    val  CD : CompositeDisposable = CompositeDisposable()
                    myAPI=MainActivity.retrofit!!.create(NodeJS::class.java)
                    CD.add(myAPI.getSongById(id)
                            .observeOn(Schedulers.io())
                            .subscribeOn(Schedulers.newThread())
                            .subscribe{
                                val myObj: JSONObject = JSONObject(it)
                                song.title=myObj.getString("title")

                            })

                }
    }

}