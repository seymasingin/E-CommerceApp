package com.seymasingin.e_commerceapp.ui.signup

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.databinding.FragmentSignUpBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding::bind)

    private val viewModel by viewModels<SignUpViewModel>()

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        with(binding) {
            btnSignUp.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (checkFields(email, password)) {
                    viewModel.signUp(email, password)
                } else {
                    Snackbar.make(requireView(), "Fill in the required blanks", 1000).show()
                }
            }
        }
        observeData()
    }

    private fun observeData() {
        viewModel.isSignUp.observe(viewLifecycleOwner){ isSignUp ->
            if (isSignUp) {
                findNavController().navigate(R.id.signUpToHome)
            } else {
                Snackbar.make(requireView(), "Sign-in failed!", 1000).show()
            }
        }
    }

    fun checkFields(email: String, password: String): Boolean {
        return when {
            Patterns.EMAIL_ADDRESS.matcher(email).matches().not() -> {
                binding.tilEmail.error = "E-Mail is not valid!"
                false
            }

            password.length < 6 -> {
                binding.tilEmail.isErrorEnabled = false
                binding.tilPassword.error = "Password length should be more than six characters!"
                false
            }

            else -> true
        }
    }
}