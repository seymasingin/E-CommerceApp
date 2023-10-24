package com.seymasingin.e_commerceapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.data.model.response.ProductListUI
import com.seymasingin.e_commerceapp.data.repository.ProductRepository
import com.seymasingin.e_commerceapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val productRepository: ProductRepository,
                                        private val userRepository: UserRepository) : ViewModel() {

    private var _cartState = MutableLiveData<HomeState>()
    val cartState: LiveData<HomeState> get() = _cartState

    val userId = userRepository.getUserId()

    fun getCartProducts(userId: String) = viewModelScope.launch {
        _cartState.value = HomeState.Loading

        _cartState.value = when (val result = productRepository.getCartProducts(userId) ){
            is Resource.Success -> HomeState.SuccessState(result.data)
            is Resource.Fail -> HomeState.EmptyScreen(result.failMessage)
            is Resource.Error -> HomeState.ShowPopUp(result.errorMessage)
        }
    }

    fun deleteFromCart(id: Int) = viewModelScope.launch {
        productRepository.deleteFromCart(id)
    }

    fun clearCart(userId: String) = viewModelScope.launch {
        productRepository.clearCart(userId)
    }
}

sealed interface HomeState {
    object Loading : HomeState
    data class SuccessState(val products: List<ProductListUI>) : HomeState
    data class EmptyScreen(val failMessage: String) : HomeState
    data class ShowPopUp(val errorMessage: String) : HomeState
}