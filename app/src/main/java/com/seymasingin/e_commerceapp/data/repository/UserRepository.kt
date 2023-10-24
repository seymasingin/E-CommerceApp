package com.seymasingin.e_commerceapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.seymasingin.e_commerceapp.common.Resource

class UserRepository {

    var isSignIn = MutableLiveData<Boolean>()

    var isSignUp = MutableLiveData<Boolean>()

    private var auth = FirebaseAuth.getInstance()

    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            isSignUp.value = true
        }.addOnFailureListener {
            isSignUp.value = false
        }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            isSignIn.value = true
        }.addOnFailureListener {
            isSignIn.value = false
        }
    }

    fun logOut() {
        auth.signOut()
    }

    fun getUserId(): String = auth.currentUser?.uid.orEmpty()
}