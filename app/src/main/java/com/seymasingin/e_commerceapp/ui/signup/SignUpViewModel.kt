package com.seymasingin.e_commerceapp.ui.signup

import android.util.Patterns
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SignUpViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private var _signUpState = MutableLiveData<SignUpState>()
    val signUpState: LiveData<SignUpState> get() = _signUpState

    fun signUp(email: String, password: String) = viewModelScope.launch {
        if (!checkFields(email, password)) {
            _signUpState.value = SignUpState.EmptyScreen("Please check the fields.")
            return@launch
        }

        _signUpState.value = SignUpState.Loading

        _signUpState.value = when (val result = userRepository.signUp(email, password)) {
            is Resource.Success -> SignUpState.SuccessState(true)
            is Resource.Fail -> SignUpState.EmptyScreen(result.failMessage)
            is Resource.Error -> SignUpState.ShowPopUp(result.errorMessage)
        }
    }

    fun checkFields(email: String, password: String): Boolean {
        return when {
            Patterns.EMAIL_ADDRESS.matcher(email).matches().not() -> {
                _signUpState.value = SignUpState.EmptyScreen("E-Mail is not valid!")
                false
            }

            password.length < 6 -> {
                _signUpState.value = SignUpState.EmptyScreen("Password length should be more than six characters!")
                false
            }

            else -> true
        }
    }
}

sealed interface SignUpState {
    object Loading : SignUpState
    data class SuccessState(val successState: Boolean) : SignUpState
    data class EmptyScreen(val failMessage: String) : SignUpState
    data class ShowPopUp(val errorMessage: String) : SignUpState
}
