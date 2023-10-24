package com.seymasingin.e_commerceapp.ui.signup

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private var _isSignUp = MutableLiveData<Boolean>()
    val isSignUp: LiveData<Boolean> get() = _isSignUp

    init{
        _isSignUp = userRepository.isSignUp
    }

    fun signUp(email: String, password: String) {
        userRepository.signUp(email,password)
    }
}