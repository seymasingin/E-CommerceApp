package com.seymasingin.e_commerceapp.ui.signup

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
import com.seymasingin.e_commerceapp.data.model.User
import com.seymasingin.e_commerceapp.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

    @AndroidEntryPoint
    class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

        private val binding by viewBinding(FragmentSignUpBinding::bind)

        private val viewModel by viewModels<SignUpViewModel>()

        override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
            super.onViewCreated(view, savedInstanceState)

            with(binding) {
                btnSignUp.setOnClickListener {
                    viewModel.signUp(
                        User(name = etName.text.toString(),
                            surname = etSurname.text.toString(),
                            email = etEmail.text.toString()
                            ), etPassword.text.toString())
                }
                btnToSignIn.setOnClickListener {
                    findNavController().navigate(R.id.signUpToSignIn)
                }
            }

            observeData()
        }

        private fun observeData() = with(binding) {
            viewModel.signUpState.observe(viewLifecycleOwner) { state ->
                when (state) {
                    SignUpState.Loading -> progressBarSignUp.visible()

                    is SignUpState.GoToHome -> {
                        progressBarSignUp.gone()
                        findNavController().navigate(R.id.signUpToHome)
                    }

                    is SignUpState.ShowPopUp -> {
                        progressBarSignUp.gone()
                        Snackbar.make(requireView(), state.errorMessage, 1000).show()
                    }
                }
            }
        }
    }