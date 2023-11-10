package com.seymasingin.e_commerceapp.ui.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.data.model.response.ProductUI
import com.seymasingin.e_commerceapp.data.repository.ProductRepository
import com.seymasingin.e_commerceapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DetailViewModel  @Inject constructor(
    private val productRepository: ProductRepository,
    private val userRepository: UserRepository
) : ViewModel() {

    private var _detailState = MutableLiveData<DetailState>()
    val detailState: LiveData<DetailState> get() = _detailState

    fun getProductDetail(id: Int) = viewModelScope.launch {
        _detailState.value = DetailState.Loading

        _detailState.value = when (val result = productRepository.getProductDetail(userRepository.getUserId(), id)) {
            is Resource.Success -> DetailState.SuccessState(result.data)
            is Resource.Fail -> DetailState.EmptyScreen(result.failMessage)
            is Resource.Error -> DetailState.ShowPopUp(result.errorMessage)
        }
    }

    fun addToCart(productId: Int) = viewModelScope.launch {
        val result = productRepository.addToCart(userRepository.getUserId(), productId)
        if (result is Resource.Success) {
            _detailState.value = DetailState.CartAddSuccess(result.data)
        }
        if (result is Resource.Fail){
            _detailState.value = DetailState.CartAddFail(result.failMessage)
        }
    }

    fun setFavoriteState(product: ProductUI) = viewModelScope.launch {
        if (product.isFav) {
            productRepository.deleteFromFavorites(product, userRepository.getUserId())
        } else {
            productRepository.addToFavorites(product, userRepository.getUserId())
        }
        getProductDetail(product.id)
    }
}

sealed interface DetailState {
    object Loading : DetailState
    data class SuccessState(val product: ProductUI) : DetailState
    data class EmptyScreen(val failMessage: String) : DetailState
    data class ShowPopUp(val errorMessage: String) : DetailState
    data class CartAddSuccess(val successMessage: String) : DetailState
    data class CartAddFail(val failMessage: String) : DetailState
}
