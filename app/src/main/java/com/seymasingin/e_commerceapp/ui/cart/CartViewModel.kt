package com.seymasingin.e_commerceapp.ui.cart

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.data.model.Product
import com.seymasingin.e_commerceapp.data.repository.ProductRepository
import com.seymasingin.e_commerceapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CartViewModel @Inject constructor(private val productRepository: ProductRepository,
                                        private val userRepository: UserRepository) : ViewModel() {

    private var _cartList = MutableLiveData<Resource<List<Product>>>()
    val cartList: LiveData<Resource<List<Product>>> get() = _cartList

    val userId = userRepository.getUserId()

    init {
        _cartList = productRepository.cartList
    }

    fun getCartProducts(userId: String) = viewModelScope.launch {
        productRepository.getCartProducts(userId)
    }

    fun deleteFromCart(id: Int) = viewModelScope.launch {
        productRepository.deleteFromCart(id)
    }

    fun clearCart(userId: String) = viewModelScope.launch {
        productRepository.clearCart(userId)
    }
}