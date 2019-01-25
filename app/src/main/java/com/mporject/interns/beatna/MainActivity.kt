package com.mporject.interns.beatna

import android.content.Context
import android.content.Intent
import android.database.sqlite.SQLiteDatabase
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.view.View
import android.widget.Button
import com.facebook.stetho.Stetho


class MainActivity : AppCompatActivity() {



    companion object {
    val retrofit =RetrofitClient.GetInstance()
}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        
        supportFragmentManager.beginTransaction().add(R.id.fragment_container,TablayoutFragment()).commit()
        val bottomNav = findViewById<BottomNavigationView>(R.id.bottom_navigation)
        bottomNav.setOnNavigationItemSelectedListener(navListener)
        Stetho.initializeWithDefaults(this)
        var myDb= DatabaseHelper(this)
        findViewById<Button>(R.id.logout_btn).setOnClickListener{
            Session(this).userName=""
            Session(this).uniqueId=""
            val LoginIntent = Intent(this, LoginActivity::class.java)
            startActivity(LoginIntent)
            finish()
        }
    }


    private val navListener = BottomNavigationView.OnNavigationItemSelectedListener { menuItem ->
        var selectedFragment: Fragment? = null
        when (menuItem.itemId) {
            R.id.nav_home -> selectedFragment = TablayoutFragment()
            R.id.nav_favs -> selectedFragment = FavoritesFragment()
            R.id.nav_playlist -> selectedFragment = PlaylistFragment()
            R.id.nav_profile -> selectedFragment = ProfileFragment()
        }
        supportFragmentManager.beginTransaction().replace(R.id.fragment_container, selectedFragment!!).commit()
        true

    }



}
