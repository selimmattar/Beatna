package com.mporject.interns.beatna

import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import io.reactivex.Flowable
import io.reactivex.FlowableEmitter
import io.reactivex.Observable
import io.reactivex.ObservableEmitter
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.exceptions.OnErrorNotImplementedException
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.apache.commons.io.FileUtils
import org.json.JSONArray
import org.reactivestreams.Subscriber
import org.reactivestreams.Subscription
import java.net.URL
import java.io.*
import java.lang.RuntimeException
import java.net.MalformedURLException
import java.text.SimpleDateFormat
import java.util.*


class UploadSongFragment : Fragment(), AdapterView.OnItemSelectedListener {

    val MoodList= arrayOf("Happy","Sad","Motivational","Studying","Gaming","Chill","Party")
    val CatList= arrayOf("Hip-Hop","Jazz","RnB","Rock","Afro","Romance","Electro")
    var selectMood_tv : TextView? = null
    var selectCat_tv : TextView? = null
    var selectedMood:String=""
    var selectedCat:String=""
    var SongId:Int=0
    val AlbumNames = ArrayList<String>()
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        println(parent!!.id)
  if(parent!!.id==2131296564) {
      if(selectCat_tv!=null) {
          selectedCat = CatList.get(position)
          selectCat_tv?.setText(selectedCat)
      }
}
        else if(parent!!.id==2131296566){
      if(selectMood_tv!=null) {
          selectedMood = MoodList.get(position)
          selectMood_tv?.setText(selectedMood)
      }
        }

    }


    val myAPI = MainActivity.retrofit!!.create(NodeJS::class.java)
    val CD = CompositeDisposable()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.song_upload,container,false)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        super.onViewCreated(view, savedInstanceState)
        selectMood_tv=view.findViewById<TextView>(R.id.selectMood_tv)
        selectCat_tv=view.findViewById<TextView>(R.id.selectCat_tv)
        val selectsong_et=view.findViewById<EditText>(R.id.selectsong_et)
        selectsong_et.keyListener=null

        val MoodSpinner = view.findViewById<Spinner>(R.id.uploadmood_sp)
        val CatSpinner = view.findViewById<Spinner>(R.id.uploadcat_sp)
MoodSpinner.onItemSelectedListener=this
CatSpinner.onItemSelectedListener=this

        if(arguments?.getString("songName")!=null)
            view.findViewById<EditText>(R.id.selectsong_et).setText(arguments?.getString("songName"))
        val upload_btn=view.findViewById<Button>(R.id.upload_btn)
        upload_btn.setOnClickListener{
            val manager = (context as AppCompatActivity).supportFragmentManager
val storagef= storageFragment()
            manager.beginTransaction().replace(R.id.fragment_container,storagef!!).addToBackStack("UploadFrag").commit()
        }
        if(arguments?.getString("imggName")!=null)
            view.findViewById<EditText>(R.id.selectimg_et).setText(arguments?.getString("imgName"))
        val uploadimg_btn=view.findViewById<Button>(R.id.uploadimg_btn)
        uploadimg_btn.setOnClickListener{
            val manager = (context as AppCompatActivity).supportFragmentManager
            val storagef= imageStorageFragment()
            manager.beginTransaction().replace(R.id.fragment_container,storagef!!).addToBackStack("UploadFrag").commit()
        }
        val albumupload_atv=view.findViewById<AutoCompleteTextView>(R.id.albumupload_atv)
        CD.add(myAPI.getAlbumInfoByUserId(Session(context).uniqueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    println(it)
                    if(!it.equals("No albums found")) {
                        val songs_data = JSONArray(it)
                        val n = songs_data.length()

                        var userName: String
                        for (i in 0 until n) {
                            val AlbumInfo = songs_data.getJSONObject(i)
                            AlbumNames.add(AlbumInfo.getString("title"))
                            userName = AlbumInfo.getString("username")
                        }
                        val AN_adapter = ArrayAdapter<String>(context!!, android.R.layout.simple_list_item_1, AlbumNames)
                        albumupload_atv.setAdapter(AN_adapter)
                    }
                })

        val submitupload_btn=view.findViewById<Button>(R.id.submitupload_btn)
        submitupload_btn.setOnClickListener{


try {
    val songUri=arguments?.getString("songUri")
    val songName=arguments?.getString("songName")
    val imgUri=arguments?.getString("imgUri")
    val imgName=arguments?.getString("imgName")
    println(songName)
    println(Uri.parse(songUri).encodedPath)
    val originalFile =File(Uri.parse(songUri).path)
    val filePart=RequestBody.create(MediaType.parse("audio/*"),originalFile)
    val file=MultipartBody.Part.createFormData("song",originalFile.name,filePart)
    val originalimgFile =File(Uri.parse(imgUri).path)
    val imgfilePart=RequestBody.create(MediaType.parse("images/*"),originalimgFile)
    val imgfile=MultipartBody.Part.createFormData("img",originalimgFile.name,imgfilePart)
    if(albumupload_atv.text.toString().isEmpty()){
    albumupload_atv.setText("Singles")
}else if(!(albumupload_atv.text.toString()  in AlbumNames)) {
    CD.add(myAPI.AddAlbum(albumupload_atv.text.toString(),Session(context).uniqueId,"default")
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe())
}
    CD.add(myAPI.UploadSong(RequestBody.create(MultipartBody.FORM,""+"fedi"),RequestBody.create(MultipartBody.FORM,""+albumupload_atv.text),RequestBody.create(MultipartBody.FORM,""+songName!!),file)
            .subscribeOn(Schedulers.io()).doOnError {
                it.stackTrace
                Log.e("My Error ",it.message,it)
            }
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                CD.add(myAPI.UploadImage(RequestBody.create(MultipartBody.FORM,""+"fedi"),RequestBody.create(MultipartBody.FORM,""+albumupload_atv.text),imgfile)
                        .subscribeOn(Schedulers.io()).doOnError {
                            it.stackTrace
                            Log.e("My Error ",it.message,it)
                        }
                        .observeOn(AndroidSchedulers.mainThread())
                        .subscribe())
            })

    val artist = User(Session(context!!).uniqueId,"fedi",Session(context!!).userName,0)
   val song = Song(1,songName,artist,selectedMood,selectedCat)
    CD.add(myAPI.AddSong(song.title,albumupload_atv.text.toString(),song.artist.uid,song.category,song.mood)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{
                val songs_data = JSONArray(it)
                val n = songs_data.length()
                for (i in 0 until n) {
                    val songd =songs_data.getJSONObject(i)
                    SongId=songd.getInt("id")
                    val calendar = Calendar.getInstance();
                    val mdformat = SimpleDateFormat("yyyy / MM / dd ");
                    val strDate = "Current Date : " + mdformat.format(calendar.getTime());
                    val post=Post(0,artist,song,strDate)
                    CD.add(myAPI.AddPost(song.artist.uid,SongId,post.created_at)
                            .subscribeOn(Schedulers.io())
                            .observeOn(AndroidSchedulers.mainThread())
                            .subscribe{
                    fragmentManager!!.popBackStackImmediate()
                            })
                }
            })


}catch (ex: MalformedURLException){
Log.e(" Exception", "Exception : " + ex.message, ex)
}

        }
    }





}