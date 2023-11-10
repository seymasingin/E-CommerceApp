package com.seymasingin.e_commerceapp.ui.splash

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.seymasingin.e_commerceapp.R
import androidx.navigation.fragment.findNavController
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SplashFragment : Fragment(R.layout.fragment_splash) {

    private val viewModel by viewModels<SplashViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        observeData()
    }

    private fun observeData() {
        viewModel.splashState.observe(viewLifecycleOwner){state ->
            when(state) {
                is SplashState.GoToHome -> {
                    findNavController().navigate(R.id.splashToHome)
                }

                is SplashState.GoToSignIn -> {
                    findNavController().navigate(R.id.splashToSignIn)
                }
            }
        }
    }
}