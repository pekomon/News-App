
package com.example.pekomon.newsapp.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.example.pekomon.newsapp.R
import com.example.pekomon.newsapp.data.db.ArticleDatabase
import com.example.pekomon.newsapp.data.repository.NewsRepository
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: NewsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val repository = NewsRepository(ArticleDatabase(this))
        val viewModelFactory = NewsViewModelFactory(application, repository)
        viewModel = ViewModelProvider(this, viewModelFactory).get(NewsViewModel::class.java)

        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController((R.id.nav_host_fragment))

        val appBarConfiguration = AppBarConfiguration(setOf(
            R.id.newsFragment, R.id.savedNewsFragment, R.id.searchFragment
        ))

        // Hide this action bar temporarily now
        //setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }
}
