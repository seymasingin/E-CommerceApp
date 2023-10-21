package com.seymasingin.e_commerceapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.data.model.Product
import com.seymasingin.e_commerceapp.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel  @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    private var _productDetail = MutableLiveData<Resource<Product>>()
    val productDetail : LiveData<Resource<Product>> get() =_productDetail

    init {
        _productDetail = productRepository.productDetail
    }

    fun getProductDetail(id: Int) = viewModelScope.launch {
        productRepository.getProductDetail(id)
    }

    fun addToCart(id: Int) {
        productRepository.
    }
}