package com.oqq.pocketrecipe.view.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.NavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import com.oqq.pocketrecipe.R
import com.oqq.pocketrecipe.databinding.ActivityRestaurantOwnerPageBinding

class ActivityRestaurantOwnerPage: AppCompatActivity() {
    private lateinit var binding:ActivityRestaurantOwnerPageBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRestaurantOwnerPageBinding.inflate(layoutInflater)
        setContentView(binding.root)


        val navHost: NavHostFragment =
            supportFragmentManager.findFragmentById(R.id.my_nav_host_fragment) as NavHostFragment

        val navController = navHost.navController

        setupNavigationMenu(navController)

        binding.bnBottom.setupWithNavController(navController)
    }


    private fun setupNavigationMenu(navController:NavController){
        val sideNavView = findViewById<NavigationView>(R.id.nav_view)
        sideNavView?.setupWithNavController(navController)
    }
}