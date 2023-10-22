package com.seymasingin.e_commerceapp.ui.signin

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.common.gone
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.common.visible
import com.seymasingin.e_commerceapp.databinding.FragmentSignInBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

    private val viewModel by viewModels<SignInViewModel>()

    private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()

        auth.currentUser?.let {
            findNavController().navigate(R.id.signInToHome)
        }

        with(binding) {
            btnSignIn.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (checkFields(email, password)) {
                    viewModel.signIn(email, password)
                }
            }
            btnToSignUp.setOnClickListener {
                findNavController().navigate(R.id.signInToSignUp)
            }
        }

        observeData()
    }

    fun observeData() {
        viewModel.isSignIn.observe(viewLifecycleOwner){
            when(it) {

                Resource.Loading -> binding.progressBarSignIn.visible()

                is Resource.Success -> {
                    binding.progressBarSignIn.gone()
                    findNavController().navigate(R.id.signInToHome)
                }

                is Resource.Fail -> {
                    binding.progressBarSignIn.gone()
                    Snackbar.make(requireView(), it.failMessage, 1000).show()
                }

                is Resource.Error -> {
                    binding.progressBarSignIn.gone()
                    Snackbar.make(requireView(), it.errorMessage, 1000).show()
                }
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