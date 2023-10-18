package com.seymasingin.e_commerceapp.ui.signin

import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.databinding.FragmentSignInBinding

class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding::bind)

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
                    signIn(email, password)
                }
            }
            btnToSignUp.setOnClickListener {
                findNavController().navigate(R.id.signInToSignUp)
            }
        }
    }

    private fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            findNavController().navigate(R.id.signInToHome)
        }.addOnFailureListener {
            Snackbar.make(requireView(), it.message.orEmpty(), 1000).show()
        }
    }

    private fun checkFields(email: String, password: String): Boolean {
        return when {
            Patterns.EMAIL_ADDRESS.matcher(email).matches().not() -> {
                binding.tilEmail.error = "E-Mail is not valid!"
                false
            }

            password.isEmpty() -> {
                binding.tilEmail.isErrorEnabled = false
                binding.tilPassword.error = "Password is empty!"
                false
            }

            password.length < 6 -> {
                binding.tilEmail.isErrorEnabled = false
                binding.tilPassword.error = "7"
                false
            }

            else -> true
        }
    }
}