package com.seymasingin.e_commerceapp.ui.payment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seymasingin.e_commerceapp.data.repository.ProductRepository
import com.seymasingin.e_commerceapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
):
    ViewModel() {

    private var _paymentState = MutableLiveData<PaymentState>()
    val paymentState: LiveData<PaymentState> get() = _paymentState

    fun clearCart() = viewModelScope.launch {
        productRepository.clearCart(userRepository.getUserId())
    }

    fun payment(number: String,
                cvc: String,
                name: String,
                city: String,
                town: String,
                address:String ) = viewModelScope.launch {

        _paymentState.value = PaymentState.Loading

        if (!checkFields(number, cvc, name, city, town, address)) {
            return@launch
        }

        else {
            _paymentState.value = PaymentState.SuccessState(true)
        }
    }

    private fun checkFields(number: String,
                            cvc: String,
                            name: String,
                            city: String,
                            town: String,
                            address:String ): Boolean {
        return when {
            number.length < 16 -> {
                _paymentState.value = PaymentState.ShowPopUp("Card number cannot be less than 16")
                false
            }
            cvc.length < 3 -> {
                _paymentState.value = PaymentState.ShowPopUp("CVC must consist of 3 digits")
                false
            }
            name.isEmpty() -> {
                _paymentState.value = PaymentState.ShowPopUp("Name can not be empty!")
                false
            }
            city.isEmpty() -> {
                _paymentState.value = PaymentState.ShowPopUp("City can not be empty!")
                false
            }
            town.isEmpty() -> {
                _paymentState.value = PaymentState.ShowPopUp("Town can not be empty!")
                false
            }
            address.isEmpty() -> {
                _paymentState.value = PaymentState.ShowPopUp("Address can not be empty!")
                false
            }

            else -> true
        }
    }
}

sealed interface PaymentState {
    object Loading : PaymentState
    data class SuccessState(val successState: Boolean) : PaymentState
    data class ShowPopUp(val errorMessage: String) : PaymentState
}