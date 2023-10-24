package com.seymasingin.e_commerceapp.ui.search

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.data.model.response.ProductListUI
import com.seymasingin.e_commerceapp.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(private val productRepository: ProductRepository) : ViewModel() {

    private var _searchState = MutableLiveData<HomeState>()
    val searchState: LiveData<HomeState> get() = _searchState

    fun getSearchProducts(query: String) = viewModelScope.launch {
        _searchState.value = HomeState.Loading

        _searchState.value = when (val result = productRepository.getSearchProducts(query)) {
            is Resource.Success -> HomeState.SuccessState(result.data)
            is Resource.Fail -> HomeState.EmptyScreen(result.failMessage)
            is Resource.Error -> HomeState.ShowPopUp(result.errorMessage)
        }
    }
}

sealed interface HomeState {
    object Loading : HomeState
    data class SuccessState(val products: List<ProductListUI>) : HomeState
    data class EmptyScreen(val failMessage: String) : HomeState
    data class ShowPopUp(val errorMessage: String) : HomeState
}