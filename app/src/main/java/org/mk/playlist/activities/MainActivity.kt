package org.mk.playlist.activities

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.navigation.Navigation.findNavController
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupWithNavController

import androidx.navigation.NavController

import androidx.navigation.fragment.NavHostFragment
import com.google.android.material.appbar.CollapsingToolbarLayout
import org.mk.playlist.R


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val navHostFragment: NavHostFragment = supportFragmentManager
//            .findFragmentById(R.id.main_content) as NavHostFragment
//        val navController: NavController = navHostFragment.navController
//        val appBarConfiguration = AppBarConfiguration(navController.graph)
//
//        val toolbar = findViewById<Toolbar>(R.id.toolbar)

    }

//    override fun onCreateOptionsMenu(menu: Menu): Boolean {
//        menuInflater.inflate(R.menu.menu_main, menu)
//        return super.onCreateOptionsMenu(menu)
//    }
}

