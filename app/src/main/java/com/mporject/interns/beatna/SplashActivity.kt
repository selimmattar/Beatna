package com.mporject.interns.beatna

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.support.v7.app.AppCompatActivity

class SplashActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.splash_activity)
        Handler().postDelayed({
            val LoginIntent = Intent(this,LoginActivity::class.java)
            startActivity(LoginIntent)
            finish()
        },3000)

    }
}