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

    @POST("AlbumSongs_getBySongId")
    @FormUrlEncoded
    fun getAlbumSongsBySongId(@Field("id")id:Int):Observable<String>

    @POST("getUserFollowers")
    @FormUrlEncoded
    fun getUserFollowers(@Field("id")id:String):Observable<String>

    @POST("getCountUserFollowers")
    @FormUrlEncoded
    fun getCountUserFollowers(@Field("id")id:String):Observable<String>

    @POST("getUserFollowed")
    @FormUrlEncoded
    fun getUserFollowed(@Field("id")id:String):Observable<String>

    @POST("getCountUserFollowed")
    @FormUrlEncoded
    fun getCountUserFollowed(@Field("id")id:String):Observable<String>

    @POST("getPostByIdUser")
    @FormUrlEncoded
    fun getPostByIdUser(@Field("id")id:String):Observable<String>

    @POST("getCountUserPosts")
    @FormUrlEncoded
    fun getCountUserPosts(@Field("id")id:String):Observable<String>

    @POST("getUserFavs")
    @FormUrlEncoded
    fun getUserFavs(@Field("id")id:String):Observable<String>

    @POST("getMoodPlaylist")
    @FormUrlEncoded
    fun getMoodPlaylist(@Field("mood")mood:String):Observable<String>

    @POST("getSongsThreeBestMoods")
    @FormUrlEncoded
    fun getSongsThreeBestMoods(@Field("mood")mood:String,@Field("limit")limit:Int):Observable<String>

    @POST("getSongsThreeBestCategories")
    @FormUrlEncoded
    fun getSongsThreeBestCategories(@Field("category")category:String):Observable<String>

}