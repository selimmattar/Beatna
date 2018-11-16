package com.mporject.interns.beatna

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class LoginActivity : AppCompatActivity(){
    var myAPI : NodeJS? = null
    var compositeDisposable : CompositeDisposable = CompositeDisposable()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.login_activity)

        val retrofit =RetrofitClient.getInstance()
        myAPI= retrofit?.create(NodeJS::class.java)
        val nextbtn = findViewById<Button>(R.id.nextbtn)
        val login_et=findViewById<EditText>(R.id.logininput)
        val pwd_et=findViewById<EditText>(R.id.pwdinput)
        nextbtn.setOnClickListener {
            View: View -> loginUser(login_et.text.toString(),pwd_et.text.toString())
        }
    }
    fun loginUser(em:String,pwd:String){
        compositeDisposable.add(myAPI?.loginUser(em,pwd)
        !!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
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