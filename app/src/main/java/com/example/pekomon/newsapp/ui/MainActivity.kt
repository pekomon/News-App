package com.example.pekomon.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pekomon.newsapp.R
import com.google.android.material.bottomnavigation.BottomNavigationItemView
import com.google.android.material.bottomnavigation.BottomNavigationView
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //
        // bottomNavigationView.setupWithNavController(newsNavHostFragment.findNavController())
        //val navHostFormatError = supportFragmentManager.findFragmentById(R.id.newsNavHostFragment)
        //val navController = newsNavHostFragment.navCon

        /*
        starts but crashes on click
        val navView: BottomNavigationView = findViewById(R.id.bottomNavigationView)
        val navController = findNavController(R.id.newsNavHostFragment)

        val appBarConfiguration = AppBarConfiguration(setOf(R.id.newsFragment, R.id.searchFragment, R.id.articleFragment))

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        */

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController((R.id.nav_host_fragment))

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.newsFragment, R.id.savedNewsFragment, R.id.searchFragment
        ))

        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        /*
        Works in newsapp2
        * val navView: BottomNavigationView = findViewById(R.id.nav_view)

        val navController = findNavController(R.id.nav_host_fragment)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        val appBarConfiguration = AppBarConfiguration(setOf(
                R.id.navigation_home, R.id.navigation_dashboard, R.id.navigation_notifications))
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
        * */


/*
* val navHostFragment = supportFragmentManager
    .findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment
val navController = navHostFragment.navController
* */

    }
}
