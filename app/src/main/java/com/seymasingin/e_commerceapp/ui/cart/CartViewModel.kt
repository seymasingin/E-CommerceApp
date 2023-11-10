package com.seymasingin.e_commerceapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.data.model.response.ProductUI
import com.seymasingin.e_commerceapp.data.repository.ProductRepository
import com.seymasingin.e_commerceapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
    ) : ViewModel() {

    private var _cartState = MutableLiveData<CartState>()
    val cartState: LiveData<CartState> get() = _cartState

    fun getCartProducts() = viewModelScope.launch {
        _cartState.value = CartState.Loading

        _cartState.value = when (val result = productRepository.getCartProducts(userRepository.getUserId())) {
            is Resource.Success -> CartState.SuccessState(result.data)
            is Resource.Fail -> CartState.EmptyScreen(result.failMessage)
            is Resource.Error -> CartState.ShowPopUp(result.errorMessage)
        }
    }

    fun deleteFromCart(id: Int) = viewModelScope.launch {
        val result = productRepository.deleteFromCart(id, userRepository.getUserId())
        if (result is Resource.Success) {
            _cartState.value = CartState.DeleteSuccess(result.data)
        }
        getCartProducts()
    }

    fun clearCart() = viewModelScope.launch {
        val result = productRepository.clearCart(userRepository.getUserId())
        if (result is Resource.Success) {
            _cartState.value = CartState.DeleteSuccess(result.data)
        }
        getCartProducts()
    }
}

sealed interface CartState {
    object Loading : CartState
    data class SuccessState(val products: List<ProductUI>) : CartState
    data class EmptyScreen(val failMessage: String) : CartState
    data class ShowPopUp(val errorMessage: String) : CartState
    data class DeleteSuccess(val successMessage: String) : CartState
}

