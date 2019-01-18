package com.mporject.interns.beatna

import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers
import org.json.JSONArray

class SignInFragment : Fragment() {
    var myAPI : NodeJS? = null
    var compositeDisposable : CompositeDisposable = CompositeDisposable()

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signin,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        var userSession = Session(context)

        myAPI= MainActivity.retrofit?.create(NodeJS::class.java)
        val nextbtn = view!!.findViewById<Button>(R.id.nextbtn)
        val signupbtn=view!!.findViewById<Button>(R.id.signupbtn)
        val login_et=view!!.findViewById<EditText>(R.id.logininput)
        val pwd_et=view!!.findViewById<EditText>(R.id.pwdinput)
        val ft = fragmentManager?.beginTransaction()
        signupbtn?.setOnClickListener{
            ft?.replace(R.id.loginFragment_container, SignUpFragment())
            ft?.commit()
        }
        nextbtn?.setOnClickListener {
            View: View -> loginUser(login_et.text.toString(),pwd_et.text.toString())
            userSession.userName= login_et.text.toString()

        }
    }
    fun loginUser(em:String,pwd:String){
        compositeDisposable.add(myAPI?.loginUser(em,pwd)
        !!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    val intent = Intent(activity, MainActivity::class.java)
                    var userSession = Session(context)

                  if(!(it.equals("Wrong password") || it.equals("User non existant"))) {
                      val posts_data = JSONArray(it)
                      val post = posts_data.getJSONObject(0)
                      val uniqueId = post.getString("unique_id")
                      userSession.uniqueId=uniqueId
                      startActivity(intent)
                  }
                    else  Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()

                }
        )

    }



    override fun onStop() {
        compositeDisposable.clear()
        super.onStop()
    }

    override fun onDestroy() {
        compositeDisposable.clear()
        super.onDestroy()
    }
}