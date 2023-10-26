package com.seymasingin.e_commerceapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.seymasingin.e_commerceapp.common.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext

class UserRepository {

    private var auth = FirebaseAuth.getInstance()

    suspend fun signUp(email: String, password: String): Resource<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val authResult = auth.createUserWithEmailAndPassword(email, password).await()
                Resource.Success(true)
            } catch (e: Exception) {
                Resource.Error("An error occurred during sign up: ${e.message}")
            }
        }
    }

    suspend fun signIn(email: String, password: String): Resource<Boolean> {
        return withContext(Dispatchers.IO) {
            try {
                val authResult = auth.signInWithEmailAndPassword(email, password).await()
                Resource.Success(true)
            } catch (e: Exception) {
                Resource.Error("An error occurred during sign in: ${e.message}")
            }
        }
    }

    fun logOut() {
        auth.signOut()
    }

    fun getUserId(): String = auth.currentUser?.uid.orEmpty()
}