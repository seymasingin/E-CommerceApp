package com.seymasingin.e_commerceapp.ui.profile

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seymasingin.e_commerceapp.data.model.User
import com.seymasingin.e_commerceapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ProfileViewModel @Inject constructor(private val userRepository: UserRepository): ViewModel() {

    private var _userState = MutableLiveData<ProfileState>()
    val userState: LiveData<ProfileState> get() = _userState

    init {
        getCurrentUser()
    }

    private fun getCurrentUser() = viewModelScope.launch {
        _userState.value = ProfileState.Loading

        _userState.value = ProfileState.SuccessState(userRepository.getUser())

    }

    fun logOut() {
        userRepository.logOut()
        _userState.value = ProfileState.GoToSignIn
    }
}

sealed interface ProfileState {
    object Loading : ProfileState
    object GoToSignIn : ProfileState
    data class SuccessState(val user: User) : ProfileState
}