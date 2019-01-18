package com.mporject.interns.beatna

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import org.apache.commons.io.FileUtils
import org.json.JSONArray
import org.json.JSONObject
import org.json.JSONTokener
import retrofit2.http.Url
import java.net.URL
import java.io.*
import java.net.HttpURLConnection
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
    override fun onNothingSelected(parent: AdapterView<*>?) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
        println(parent!!.id)
if(parent!!.id==2131296557) {
    selectedCat = CatList.get(position)
    selectCat_tv?.setText(selectedCat)
}
        else if(parent!!.id==2131296558){
    selectedMood=MoodList.get(position)
    selectMood_tv?.setText(selectedMood)
        }

    }

    private val PICK_FILE_REQUEST = 1
    private val TAG = MainActivity::class.java.simpleName
    private val selectedFilePath: String? = null
    private val SERVER_URL = "http://coderefer.com/extras/UploadToServer.php"
    private var serverResponseCode=-1
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
        //String array.
       // val myStrings = arrayOf("Happy", "Sad", "Party", "Sleep", "Motivational","Chill","Love","Studying","Gaming")
        val MoodSpinner = view.findViewById<Spinner>(R.id.uploadmood_sp)
        val CatSpinner = view.findViewById<Spinner>(R.id.uploadcat_sp)
MoodSpinner.onItemSelectedListener=this
CatSpinner.onItemSelectedListener=this
        //Adapter for spinner
      //  MoodSpinner.adapter = ArrayAdapter(context, android.R.layout.simple_spinner_dropdown_item, myStrings)

        MoodSpinner.setSelection(1)
        if(arguments?.getString("songName")!=null)
            view.findViewById<EditText>(R.id.selectsong_et).setText(arguments?.getString("songName"))
        val upload_btn=view.findViewById<Button>(R.id.upload_btn)
        upload_btn.setOnClickListener{
            val manager = (context as AppCompatActivity).supportFragmentManager
val storagef= storageFragment()
            manager.beginTransaction().replace(R.id.fragment_container,storagef!!).addToBackStack(null).commit()
        }
        val albumupload_atv=view.findViewById<AutoCompleteTextView>(R.id.albumupload_atv)
        CD.add(myAPI.getAlbumInfoByUserId(Session(context).uniqueId)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    val songs_data = JSONArray(it)
                    val n = songs_data.length()
                    val AlbumNames= ArrayList<String>()
                    var userName:String
                    for (i in 0 until n) {
                        val AlbumInfo =songs_data.getJSONObject(i)
                        AlbumNames.add(AlbumInfo.getString("title"))
                        userName=AlbumInfo.getString("username")
                    }
                    val AN_adapter=ArrayAdapter<String>(context!!,android.R.layout.simple_list_item_1,AlbumNames)
                    albumupload_atv.setAdapter(AN_adapter)
                })
        if(albumupload_atv.listSelection!=ListView.INVALID_POSITION){
        val songUri=arguments?.getString("songUri")
            val originalFile =FileUtils.toFile(URL(songUri))
            val filePart=RequestBody.create(MediaType.parse(context!!.contentResolver.getType(Uri.parse(songUri))),originalFile)
            val file=MultipartBody.Part.createFormData("song",originalFile.name,filePart)
          /*  CD.add(myAPI.UploadSong( RequestBody.create(MediaType.parse("text/plain"),"seeeelim"),file)
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe{})*/
        }
        val submitupload_btn=view.findViewById<Button>(R.id.submitupload_btn)
        submitupload_btn.setOnClickListener{
try {
    val songUri=arguments?.getString("songUri")
    val songName=arguments?.getString("songName")
    println(songName)
    println(Uri.parse(songUri).encodedPath)
    val originalFile =File(Uri.parse(songUri).path)
    val filePart=RequestBody.create(MediaType.parse("audio/*"),originalFile)
    val file=MultipartBody.Part.createFormData("song",originalFile.name,filePart)
    CD.add(myAPI.UploadSong(RequestBody.create(MultipartBody.FORM,""+"fedi"),RequestBody.create(MultipartBody.FORM,""+albumupload_atv.text),RequestBody.create(MultipartBody.FORM,""+songName!!),file)
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe{})
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
                    val post=Post(artist,song,strDate.toString())
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

   /* fun uploadFile(sourceFileUri: String) {


        var conn: HttpURLConnection? = null
        var dos: DataOutputStream? = null
        val lineEnd = "\r\n"
        val twoHyphens = "--"
        val boundary = "*****"
        var bytesRead: Int
        var bytesAvailable: Int
        var bufferSize: Int
        val buffer: ByteArray
        val maxBufferSize = 1 * 1024 * 1024
        val sourceFile = File(sourceFileUri)

        if (!sourceFile.isFile) {

            /*  dialog.dismiss();

            Log.e("uploadFile", "Source File not exist :"+imagepath);

            runOnUiThread(new Runnable() {
                public void run() {
                    messageText.setText("Source File not exist :"+ imagepath);
                }
            });*/



        } else {
            try {

                // open a URL connection to the Servlet
                val fileInputStream = FileInputStream(sourceFile)
                val url = URL("http://172.16.197.255/Server/upload.php")

                // Open a HTTP  connection to  the URL
                conn = url.openConnection() as HttpURLConnection
                conn.doInput = true // Allow Inputs
                conn.doOutput = true // Allow Outputs
                conn.useCaches = false // Don't use a Cached Copy
                conn.requestMethod = "POST"
                conn.setRequestProperty("Connection", "Keep-Alive")
                conn.setRequestProperty("ENCTYPE", "multipart/form-data")
                conn.setRequestProperty("Content-Type", "multipart/form-data;boundary=$boundary")
                conn.setRequestProperty("file", sourceFileUri)

                dos = DataOutputStream(conn.outputStream)

                dos.writeBytes(twoHyphens + boundary + lineEnd)
                dos.writeBytes("Content-Disposition: form-data; name=\"file\";filename=\""
                        + sourceFileUri + "\"" + lineEnd)

                dos.writeBytes(lineEnd)

                // create a buffer of  maximum size
                bytesAvailable = fileInputStream.available()

                bufferSize = Math.min(bytesAvailable, maxBufferSize)
                buffer = ByteArray(bufferSize)

                // read file and write it into form...
                bytesRead = fileInputStream.read(buffer, 0, bufferSize)

                while (bytesRead > 0) {

                    dos.write(buffer, 0, bufferSize)
                    bytesAvailable = fileInputStream.available()
                    bufferSize = Math.min(bytesAvailable, maxBufferSize)
                    bytesRead = fileInputStream.read(buffer, 0, bufferSize)

                }

                // send multipart form data necesssary after file data...
                dos.writeBytes(lineEnd)
                dos.writeBytes(twoHyphens + boundary + twoHyphens + lineEnd)

                // Responses from the server (code and message)
                serverResponseCode = conn.responseCode
                val serverResponseMessage = conn.responseMessage

                Log.i("uploadFile", "HTTP Response is : "
                        + serverResponseMessage + ": " + serverResponseCode)

                if (serverResponseCode == 200) {

                    /*runOnUiThread(new Runnable() {
                        public void run() {
                            String msg = "File Upload Completed.\n\n See uploaded file here : \n\n"
                                    +" C:/xamp/wamp/fileupload/uploads";
                            messageText.setText(msg);
                            Toast.makeText(MainActivity.this, "File Upload Complete.", Toast.LENGTH_SHORT).show();
                        }
                    });*/
                }

                //close the streams //
                fileInputStream.close()
                dos.flush()
                dos.close()

            } catch (ex: MalformedURLException) {

                // dialog.dismiss();
                ex.printStackTrace()

                /*  runOnUiThread(new Runnable() {
                    public void run() {
                        messageText.setText("MalformedURLException Exception : check script url.");
                        Toast.makeText(MainActivity.this, "MalformedURLException", Toast.LENGTH_SHORT).show();
                    }
                });*/

                Log.e("Upload file to server", "error: " + ex.message, ex)
            } catch (e: Exception) {

/* dialog.dismiss();
                e.printStackTrace();

                runOnUiThread(new Runnable() {
                    public void run() {
                       messageText.setText("Got Exception : see logcat ");
                        Toast.makeText(MainActivity.this, "Got Exception : see logcat ", Toast.LENGTH_SHORT).show();
                    }
                });*/
                Log.e("Upload file to server Exception", "Exception : " + e.message, e)
}

 //  dialog.dismiss();


}
}*/



}