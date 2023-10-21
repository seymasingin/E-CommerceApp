package com.seymasingin.e_commerceapp.ui.search

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
class SearchViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    private var _searchProductsList = MutableLiveData<Resource<List<Product>>>()
    val searchProductsList: LiveData<Resource<List<Product>>> get() = _searchProductsList

    init {
        _searchProductsList = productRepository.searchProductsList
    }

    fun getSearchProducts(query: String) = viewModelScope.launch {
        productRepository.getSearchProducts(query)
    }
}