package com.seymasingin.e_commerceapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.data.model.response.ProductUI
import com.seymasingin.e_commerceapp.data.repository.ProductRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel  @Inject constructor(private val productRepository: ProductRepository) :
    ViewModel() {

    private var _detailState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState> get() = _detailState

    private var _addCartState = MutableLiveData<AddCartState>()
    val addCartState: LiveData<AddCartState> get() = _addCartState

    fun getProductDetail(id: Int) = viewModelScope.launch {
        _detailState.value = DetailState.Loading

        _detailState.value = when (val result = productRepository.getProductDetail(id)) {
            is Resource.Success -> DetailState.SuccessState(result.data)
            is Resource.Fail -> DetailState.EmptyScreen(result.failMessage)
            is Resource.Error -> DetailState.ShowPopUp(result.errorMessage)
        }
    }

    fun addToCart(productId: Int) = viewModelScope.launch {
        val result = productRepository.addToCart(productId)
        if (result is Resource.Success) {
            _addCartState.value = AddCartState.CartAddSuccess(result.data)
        }
        if (result is Resource.Fail){
            _addCartState.value = AddCartState.CartAddFail(result.failMessage)
        }
    }

    fun setFavoriteState(product: ProductUI) = viewModelScope.launch {
        if (product.isFav) {
            productRepository.deleteFromFavorites(product)
        } else {
            productRepository.addToFavorites(product)
        }
        getProductDetail(product.id)
    }
}

sealed interface DetailState {
    object Loading : DetailState
    data class SuccessState(val product: ProductUI) : DetailState
    data class EmptyScreen(val failMessage: String) : DetailState
    data class ShowPopUp(val errorMessage: String) : DetailState
}

sealed interface AddCartState {
    data class CartAddSuccess(val successMessage: String) : AddCartState
    data class CartAddFail(val failMessage: String) : AddCartState
}