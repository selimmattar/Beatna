package com.mporject.interns.beatna

import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray
import org.json.JSONObject

class User(var uid: String,  var name :String, var email :String, var role :Int  ) {





     companion object {

         fun getUserByUid(uid:String):User{
           var user=User("","","",0)
             val myAPI : NodeJS
             val  CD = CompositeDisposable()
             myAPI=MainActivity.retrofit!!.create(NodeJS::class.java)
             CD.add(myAPI.getUserByUid(uid)
                     .observeOn(Schedulers.io())
                     .subscribeOn(Schedulers.newThread())
                     .subscribe{
                         val myObj=JSONObject(it)

                         user.name=myObj.getString("name")
                         user.email=myObj.getString("email")
                         user.role=myObj.getInt("role")
                         user.uid=myObj.getString("unique_id")


                     })
            return user
         }
     }

}