package com.seymasingin.e_commerceapp.data.repository

import com.google.firebase.auth.FirebaseAuth
import com.seymasingin.e_commerceapp.common.Resource
import kotlinx.coroutines.tasks.await

class UserRepository (private val firebaseAuth: FirebaseAuth){

    suspend fun signUp(email: String, password: String): Resource<Unit> {
        return try {

            val result = firebaseAuth.createUserWithEmailAndPassword(email, password).await()

            if (result.user != null) {
                Resource.Success(Unit)
            } else {
                Resource.Error("An error occurred during sign up")
            }
        } catch (e: Exception) {
            Resource.Error(e.message.orEmpty())
        }
    }


    suspend fun signIn(email: String, password: String): Resource<Unit> {

        return try {

            val result = firebaseAuth.signInWithEmailAndPassword(email, password).await()

            if (result.user != null) {
                Resource.Success(Unit)
            } else {
                Resource.Error("An error occurred during sign in")
            }
        } catch (e: Exception) {
            Resource.Error("Password or email is incorrect")
        }
    }

    fun getUserId()  = firebaseAuth.currentUser?.uid.orEmpty()

    fun logOut() = firebaseAuth.signOut()

    fun isUserLoggedIn(): Boolean  {
        return firebaseAuth.currentUser!= null
    }
}