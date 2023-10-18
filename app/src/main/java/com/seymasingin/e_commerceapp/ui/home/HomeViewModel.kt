package com.seymasingin.e_commerceapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.seymasingin.e_commerceapp.data.model.Product
import com.seymasingin.e_commerceapp.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    private var _productsList = MutableLiveData<List<Product>>()
    val productsList: LiveData<List<Product>> get() = _productsList

    private var _saleProductsList = MutableLiveData<List<Product>>()
    val saleProductsList: LiveData<List<Product>> get() = _saleProductsList

    init {
        _productsList = productRepository.productsList
    }

    fun getProducts() {
        productRepository.getProducts()
    }

    fun getSaleProducts() {
        productRepository.getSaleProducts()
    }
}