package com.seymasingin.e_commerceapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private val binding by viewBinding(ActivityMainBinding::inflate)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)

        MainApplication.provideRetrofit(this)

        val navHostFragment = supportFragmentManager.findFragmentById(R.id.navHostFragment) as NavHostFragment
        NavigationUI.setupWithNavController(binding.bottomNav, navHostFragment.navController)

        navHostFragment.navController.addOnDestinationChangedListener{ _, destination, _ ->
            if(destination.id == R.id.splashFragment ||
                destination.id == R.id.signUpFragment ||
                destination.id == R.id.signInFragment ||
                destination.id == R.id.successFragment){
                binding.bottomNav.visibility = View.GONE
            }
            else{
                binding.bottomNav.visibility = View.VISIBLE
            }
        }
    }
}