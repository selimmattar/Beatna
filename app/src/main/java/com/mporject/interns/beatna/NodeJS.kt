package com.mporject.interns.beatna

import io.reactivex.Observable
import io.reactivex.Observer
import io.reactivex.observers.DisposableObserver
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface NodeJS {
    @POST("login")
    @FormUrlEncoded
    fun loginUser(@Field("email")email:String,@Field("password")password:String) : Observable<String>

    @POST("register")
    @FormUrlEncoded
    fun registerUser(@Field("name")name:String,@Field("email")email:String,@Field("password")password:String) : Observable<String>

    @POST("post_getAll")
    @FormUrlEncoded
    fun post_getAll(@Field("uid")uid:String) : Observable<String>

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

    @POST("getAllDiscussions")
    fun getAllDiscussions():Observable<String>

    @POST("getCommentsByDiscussion")
    @FormUrlEncoded
    fun getCommentsByDiscussion(@Field("id")id:Int):Observable<String>

    @POST("addComment")
    @FormUrlEncoded
    fun addComment(@Field("discussionComment")discussionComment:Int,@Field("idUser")idUser:String,@Field("contentComment")contentComment:String) : Observable<String>

    @POST("getUserInfos")
    @FormUrlEncoded
    fun getUserInfos(@Field("id")id:String):Observable<String>

    @POST("checkAbonnement")
    @FormUrlEncoded
    fun checkAbonnement(@Field("follower")follower:String,@Field("followed")followed:String):Observable<String>

    @POST("addAbonnement")
    @FormUrlEncoded
    fun addAbonnement(@Field("follower")follower:String,@Field("followed")followed:String):Observable<String>

    @POST("deleteAbonnement")
    @FormUrlEncoded
    fun deleteAbonnement(@Field("follower")follower:String,@Field("followed")followed:String):Observable<String>

    @POST("getAlbumInfoByUserId")
    @FormUrlEncoded
    fun getAlbumInfoByUserId(@Field("id")id:String) : Observable<String>

    @POST("uploadSong")
    @Multipart
    fun UploadSong(@Part("username")username:RequestBody,@Part("album")album:RequestBody,@Part("songName")songName:RequestBody,@Part song : MultipartBody.Part) : Observable<String>

    @POST("AddSong")
    @FormUrlEncoded
    fun AddSong(@Field("songtitle")songtitle : String,@Field("albumtitle")albumtitle : String,@Field("uid")uid : String,@Field("category")category : String,@Field("mood")mood : String):Observable<String>

    @POST("AddPost")
    @FormUrlEncoded
    fun AddPost(@Field("uid")uid : String,@Field("songId")song : Int,@Field("created_at")created_at : String):Observable<String>

    @POST("AddAlbum")
    @FormUrlEncoded
    fun AddAlbum(@Field("title")title : String,@Field("uid")uid : String,@Field("image")image : String):Observable<String>

    @POST("AddPostLike")
    @FormUrlEncoded
    fun AddPostLike(@Field("uid")uid : String,@Field("post")post : Int,@Field("action")action : String):Observable<String>

    @POST("uploadImage")
    @Multipart
    fun UploadImage(@Part("username")username:RequestBody,@Part("album")album:RequestBody,@Part image : MultipartBody.Part) : Observable<String>

}