package com.seymasingin.e_commerceapp.ui.home

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
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    private var _productsList = MutableLiveData<Resource<List<Product>>>()
    val productsList: LiveData<Resource<List<Product>>> get() = _productsList

    private var _saleProductsList = MutableLiveData<Resource<List<Product>>>()
    val saleProductsList: LiveData<Resource<List<Product>>> get() = _saleProductsList

    init {
        _productsList = productRepository.productsList
        _saleProductsList = productRepository.saleProductsList
    }

    fun getProducts() = viewModelScope.launch {
        productRepository.getProducts()
    }

    fun getSaleProducts() = viewModelScope.launch {
        productRepository.getSaleProducts()
    }
}