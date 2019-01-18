package com.mporject.interns.beatna

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.splash_activity)

        var session = Session(this)
        println("uid="+session.uniqueId)
        println("email="+session.userName)
        if(session.uniqueId.equals("")) {
            Handler().postDelayed({

                val LoginIntent = Intent(this, LoginActivity::class.java)
                startActivity(LoginIntent)
                finish()
            }, 3000)
        }
        else{
            Handler().postDelayed({
                val LoginIntent = Intent(this, MainActivity::class.java)
                startActivity(LoginIntent)
                finish()

            }, 3000)
        }
    }
}