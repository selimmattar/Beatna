package com.mporject.interns.beatna

import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST
import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.observers.DisposableObserver
import retrofit2.http.Field
import retrofit2.http.GET

interface NodeJS {
    @POST("login")
    @FormUrlEncoded
    fun loginUser(@Field("email")email:String,@Field("password")password:String) : Observable<String>

    @POST("displaySongs")
    fun displaySongs() : Observable<String>
}