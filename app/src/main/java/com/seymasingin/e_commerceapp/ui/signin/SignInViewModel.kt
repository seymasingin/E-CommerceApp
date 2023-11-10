package com.seymasingin.e_commerceapp.ui.signin

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
class SignInViewModel @Inject constructor(private val userRepository: UserRepository) : ViewModel() {

    private var _signInState = MutableLiveData<SignInState>()
    val signInState: LiveData<SignInState> get() = _signInState

    fun signIn(email: String, password: String) = viewModelScope.launch {
        if (!checkFields(email, password)) {


            _signInState.value = SignInState.Loading

            _signInState.value = when (val result = userRepository.signIn(email, password)) {
                is Resource.Success -> SignInState.GoToHome
                is Resource.Fail -> SignInState.ShowPopUp(result.failMessage)
                is Resource.Error -> SignInState.ShowPopUp(result.errorMessage)
            }
        }
    }

    private fun checkFields(email: String, password: String): Boolean {
        return when {
            Patterns.EMAIL_ADDRESS.matcher(email).matches().not() -> {
                _signInState.value = SignInState.ShowPopUp("E-Mail is not valid!")
                false
            }

            password.length < 6 -> {
                _signInState.value = SignInState.ShowPopUp("Password length should be more than six characters!")
                false
            }

            else -> true
        }
    }
}

sealed interface SignInState {
    object Loading : SignInState
    object GoToHome: SignInState
    data class ShowPopUp(val errorMessage: String) : SignInState

}