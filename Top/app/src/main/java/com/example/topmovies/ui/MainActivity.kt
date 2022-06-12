package com.example.topmovies.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.NavController
import androidx.navigation.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.topmovies.R
import com.example.topmovies.databinding.ActivityMainBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    //Binding
    private lateinit var binding: ActivityMainBinding

    //NavController
    private lateinit var navController: NavController
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        //set View
        binding.apply {
            //set navController
            navController = findNavController(R.id.navHost)
            //set Bottom Nav
            bottomNavigationView.setupWithNavController(navController)
            //Handle bottom nav visibility
            navController.addOnDestinationChangedListener { _, destination, _ ->
                if (
                    destination.id == R.id.splashFragment
                    || destination.id == R.id.detail
                    || destination.id == R.id.registerFragment
                ) {
                    bottomNavigationView.visibility = View.GONE
                } else {
                    bottomNavigationView.visibility = View.VISIBLE
                }

            }
        }
    }

    //Handle back button
    override fun onNavigateUp(): Boolean {
        navController.addOnDestinationChangedListener { _, destination, _ ->
            if (
                destination.id == R.id.searchFragment
                || destination.id == R.id.detail
                || destination.id == R.id.favoriteFragment
            ) {
                navController.navigate(R.id.action_to_home)
            } else if (destination.id == R.id.homeFragment) {
                finish()
            }
        }
        return super.onNavigateUp()
    }
}