package com.seymasingin.e_commerceapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.databinding.FragmentSplashBinding
import androidx.navigation.fragment.findNavController

class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val binding by viewBinding(FragmentSplashBinding::bind)
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

    Handler(Looper.getMainLooper()).postDelayed({
        findNavController().navigate(SplashFragmentDirections.splashToHome())
    }, 3000)
    }
}