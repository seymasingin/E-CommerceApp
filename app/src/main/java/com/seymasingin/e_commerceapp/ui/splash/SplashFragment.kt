package com.seymasingin.e_commerceapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import com.seymasingin.e_commerceapp.R
import androidx.navigation.fragment.findNavController
import com.google.firebase.auth.FirebaseAuth

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        Handler(Looper.getMainLooper()).postDelayed({
            if (auth.currentUser != null) {
                findNavController().navigate(SplashFragmentDirections.splashToHome())
            } else {
                findNavController().navigate(SplashFragmentDirections.splashToSignIn())
            }
        }, 3000)
    }
}