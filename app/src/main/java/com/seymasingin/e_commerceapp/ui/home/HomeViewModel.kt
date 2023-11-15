package com.seymasingin.e_commerceapp.ui.home

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.data.model.User
import com.seymasingin.e_commerceapp.data.model.response.ProductUI
import com.seymasingin.e_commerceapp.data.repository.ProductRepository
import com.seymasingin.e_commerceapp.data.repository.UserRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val productRepository: ProductRepository,
                                        private val userRepository: UserRepository) : ViewModel() {

    private var _productState = MutableLiveData<HomeState>()
    val productState: LiveData<HomeState> get() = _productState

    private var _saleProductsState = MutableLiveData<HomeState>()
    val saleProductsState: LiveData<HomeState> get() = _saleProductsState

    private var _userState = MutableLiveData<HomeState>()
    val userState: LiveData<HomeState> get() = _userState

    private var _catState = MutableLiveData<HomeState>()
    val catState: LiveData<HomeState> get() = _catState

    fun getCategories() = viewModelScope.launch {
        _catState.value = when ( val result = productRepository.getCategories()) {
            is Resource.Success -> HomeState.CategoryState(result.data)
            is Resource.Fail -> HomeState.ShowPopUp(result.failMessage)
            is Resource.Error -> HomeState.ShowPopUp(result.errorMessage)
        }
    }

    fun getProductsByCategory(category: String) = viewModelScope.launch {
        _productState.value = HomeState.Loading

        _productState.value = when ( val result = productRepository.getProductsByCategory(category,userRepository.getUserId())) {
            is Resource.Success -> HomeState.SuccessState(result.data)
            is Resource.Fail -> HomeState.EmptyScreen(result.failMessage)
            is Resource.Error -> HomeState.ShowPopUp(result.errorMessage)
        }
    }

    fun getCurrentUser() = viewModelScope.launch {
        _userState.value = HomeState.UserState(userRepository.getUser())
    }

    fun getProducts() = viewModelScope.launch {
        _productState.value = HomeState.Loading

        _productState.value = when ( val result = productRepository.getProducts(userRepository.getUserId())) {
            is Resource.Success -> HomeState.SuccessState(result.data)
            is Resource.Fail -> HomeState.EmptyScreen(result.failMessage)
            is Resource.Error -> HomeState.ShowPopUp(result.errorMessage)
        }
    }

    fun getSaleProducts() = viewModelScope.launch {
        _saleProductsState.value = HomeState.Loading

        _saleProductsState.value = when ( val result = productRepository.getSaleProducts()) {
            is Resource.Success -> HomeState.SuccessState(result.data)
            is Resource.Fail -> HomeState.EmptyScreen(result.failMessage)
            is Resource.Error -> HomeState.ShowPopUp(result.errorMessage)
        }
    }

    //private var currentCategory: String? = null

    fun setFavoriteState(product: ProductUI) = viewModelScope.launch {
        if (product.isFav) {
            productRepository.deleteFromFavorites(product, userRepository.getUserId())
        } else {
            productRepository.addToFavorites(product, userRepository.getUserId())
        }
        getProducts()
    }
}

sealed interface HomeState {
    object Loading : HomeState
    data class SuccessState(val products: List<ProductUI>) : HomeState
    data class EmptyScreen(val failMessage: String) : HomeState
    data class ShowPopUp(val errorMessage: String) : HomeState
    data class UserState(val user: User) : HomeState
    data class CategoryState(val categories: List<String>) : HomeState
}