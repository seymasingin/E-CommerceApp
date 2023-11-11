package com.seymasingin.e_commerceapp.ui.splash

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seymasingin.e_commerceapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SplashViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    private var _splashState = MutableLiveData<SplashState>()
    val splashState: LiveData<SplashState> get() = _splashState

    init{
        viewModelScope.launch {
            delay(3000)
            checkCurrentUser()
        }
    }

    private fun checkCurrentUser()= viewModelScope.launch {
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