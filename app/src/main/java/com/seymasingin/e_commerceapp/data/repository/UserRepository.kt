package com.seymasingin.e_commerceapp.data.repository

import androidx.lifecycle.MutableLiveData
import com.google.firebase.auth.FirebaseAuth
import com.seymasingin.e_commerceapp.common.Resource

class UserRepository {

    var isSignIn = MutableLiveData<Resource<Boolean>>()

    var isSignUp = MutableLiveData<Resource<Boolean>>()

    private var auth = FirebaseAuth.getInstance()

    fun signUp(email: String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnSuccessListener {
            isSignUp.value = Resource.Success(true)
        }.addOnFailureListener { e->
            isSignUp.value = Resource.Error(e.message.orEmpty())
        }
    }

    fun signIn(email: String, password: String) {
        auth.signInWithEmailAndPassword(email, password).addOnSuccessListener {
            isSignIn.value = Resource.Success(true)
        }.addOnFailureListener { e->
            isSignIn.value = Resource.Error(e.message.orEmpty())
        }
    }

    fun signOut() {

    }

    fun getUserId(): String = auth.currentUser?.uid.orEmpty()
}