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

class SignUpFragment : Fragment() {
    var myAPI : NodeJS? = null
    var compositeDisposable : CompositeDisposable = CompositeDisposable()
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.signup,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        myAPI= MainActivity.retrofit?.create(NodeJS::class.java)
        val nextbtn = view!!.findViewById<Button>(R.id.nextbtn)
        val signinbtn=view!!.findViewById<Button>(R.id.signinbtn)
        val name_et=view!!.findViewById<EditText>(R.id.nameinput)
        val email_et=view!!.findViewById<EditText>(R.id.emailinput)
        val pwd_et=view!!.findViewById<EditText>(R.id.pwdinput)
        val ft = fragmentManager?.beginTransaction()
        signinbtn?.setOnClickListener{
            ft?.replace(R.id.loginFragment_container, SignInFragment())
            ft?.commit()
        }
        nextbtn?.setOnClickListener {
            View: View -> RegisterUser(name_et.text.toString(),email_et.text.toString(),pwd_et.text.toString())
        }
    }
    fun RegisterUser(nm:String,em:String,pwd:String){
        compositeDisposable.add(myAPI?.registerUser(nm,em,pwd)
        !!.subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe{
                    Toast.makeText(activity, it, Toast.LENGTH_SHORT).show()
                    val intent= Intent(activity,MainActivity::class.java)
                    startActivity(intent);
                }
        )
    }
}