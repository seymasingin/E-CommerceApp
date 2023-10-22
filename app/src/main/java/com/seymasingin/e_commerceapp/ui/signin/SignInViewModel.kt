package com.seymasingin.e_commerceapp.ui.signin

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignInViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private var _isSignIn = MutableLiveData<Resource<Boolean>>()
    val isSignIn: LiveData<Resource<Boolean>> get() = _isSignIn

    init{
        _isSignIn = userRepository.isSignIn
    }

    fun signIn(email: String, password: String) {
        userRepository.signIn(email,password)
    }
}