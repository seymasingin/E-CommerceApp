package com.seymasingin.e_commerceapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seymasingin.e_commerceapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    private var _splashState = MutableLiveData<SplashState>()
    val splashState: LiveData<SplashState> get() = _splashState

    fun checkCurrentUser() {
        if(userRepository.isUserLoggedIn()){
            _splashState.value = SplashState.GoToHome
        }
        else {
            _splashState.value = SplashState.GoToSignIn
        }
    }
}

sealed interface SplashState {
    object GoToHome : SplashState
    object GoToSignIn : SplashState
}