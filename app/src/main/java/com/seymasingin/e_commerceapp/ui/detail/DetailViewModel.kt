package com.seymasingin.e_commerceapp.ui.detail

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
class DetailViewModel  @Inject constructor(private val productRepository: ProductRepository,
                                            private val userRepository: UserRepository) : ViewModel() {

    private var _productDetail = MutableLiveData<Resource<Product>>()
    val productDetail : LiveData<Resource<Product>> get() =_productDetail

    val userId = userRepository.getUserId()

    init {
        _productDetail = productRepository.productDetail
    }

    fun getProductDetail(id: Int) = viewModelScope.launch {
        productRepository.getProductDetail(id)
    }

    fun addToCart(userId: String, productId: Int) = viewModelScope.launch {
        productRepository.addToCart(userId, productId)
    }
}