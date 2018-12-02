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

    @POST("register")
    @FormUrlEncoded
    fun registerUser(@Field("name")name:String,@Field("email")email:String,@Field("password")password:String) : Observable<String>

    @POST("post_getAll")
    fun post_getAll() : Observable<String>

    @POST("displaySongs")
    fun displaySongs() : Observable<String>

    @POST("user_getByUid")
    @FormUrlEncoded
    fun getUserByUid(@Field("uid")uid:String):Observable<String>

    @POST("song_getById")
    @FormUrlEncoded
    fun getSongById(@Field("id")id:Int):Observable<String>

    @POST("getAlbumInfo")
    fun getAlbumInfo() : Observable<String>

    @POST("/AlbumSongs_getBySongId")
    @FormUrlEncoded
    fun getAlbumSongsBySongId(@Field("id")id:Int):Observable<String>

}