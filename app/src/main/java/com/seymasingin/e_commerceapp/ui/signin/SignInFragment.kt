package com.seymasingin.e_commerceapp.ui.signin

import android.os.Bundle
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.gone
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.common.visible
import com.seymasingin.e_commerceapp.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel by viewModels<SignInViewModel>()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                viewModel.signIn(email, password)
            }
            btnToSignUp.setOnClickListener {
                findNavController().navigate(R.id.signInToSignUp)
            }
        }

        observeData()
    }

    private fun observeData() = with(binding) {
        viewModel.signInState.observe(viewLifecycleOwner){ state ->
            when (state) {
                SignInState.Loading -> progressBarSignIn.visible()

                is SignInState.SuccessState -> {
                    progressBarSignIn.gone()
                    findNavController().navigate(R.id.signInToHome)
                }

                is SignInState.EmptyScreen -> {
                    progressBarSignIn.gone()
                    icSignInEmpty.visible()
                    tvSignInEmpty.visible()
                    tvSignInEmpty.text = state.failMessage
                }

                is SignInState.ShowPopUp -> {
                    progressBarSignIn.gone()
                    Snackbar.make(requireView(), state.errorMessage, 1000).show()
                }
            }
        }
    }
}