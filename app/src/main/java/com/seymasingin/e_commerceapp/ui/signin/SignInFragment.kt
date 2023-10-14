package com.seymasingin.e_commerceapp.ui.signin


import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.databinding.FragmentSignInBinding


class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val binding by viewBinding(FragmentSignInBinding:: bind)

    //private lateinit var auth: FirebaseAuth

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        with(binding) {
            btnSignIn.setOnClickListener {

            }
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
                binding.tilPassword.error = "Password length should be more than six characters!"
                false
            }
            else -> true
        }
    }
}