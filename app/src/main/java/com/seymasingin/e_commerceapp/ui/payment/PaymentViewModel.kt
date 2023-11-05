package com.seymasingin.e_commerceapp.ui.payment

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.data.repository.ProductRepository
import com.seymasingin.e_commerceapp.data.repository.UserRepository
import com.seymasingin.e_commerceapp.ui.cart.CartState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class PaymentViewModel @Inject constructor(private val productRepository: ProductRepository):
    ViewModel() {

    fun clearCart() = viewModelScope.launch {
        productRepository.clearCart()
    }
}