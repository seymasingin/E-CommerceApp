package com.seymasingin.e_commerceapp.ui.signup


import android.os.Bundle
import android.util.Patterns
import android.view.View
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.seymasingin.e_commerceapp.R
import com.seymasingin.e_commerceapp.common.viewBinding
import com.seymasingin.e_commerceapp.databinding.FragmentSignUpBinding


class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val binding by viewBinding(FragmentSignUpBinding:: bind)

    private lateinit var auth: FirebaseAuth
    private lateinit var db: FirebaseFirestore
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        auth = FirebaseAuth.getInstance()
        db = FirebaseFirestore.getInstance()

        with(binding) {
            btnSignUp.setOnClickListener {
                val email = etEmail.text.toString()
                val password = etPassword.text.toString()
                if (checkFields(email, password)) {
                    signUp(email,password)
                }
                else {
                    Snackbar.make(requireView(), "Fill in the required blanks", 1000).show()
                }
            }
        }
    }
   private fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            findNavController().navigate(R.id.signUpToHome)
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
            password.length < 6 -> {
                binding.tilEmail.isErrorEnabled = false
                binding.tilPassword.error = "Password length should be more than six characters!"
                false
            }
            else -> true
        }
    }
}