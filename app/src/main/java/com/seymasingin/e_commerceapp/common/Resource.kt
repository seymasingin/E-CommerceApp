package com.seymasingin.e_commerceapp.common

import com.seymasingin.e_commerceapp.data.model.Product

sealed class Resource<out T : Any> {
    data class Success<out T : Any>(val data: T) : Resource<T>()
    data class Error(val errorMessage: String) : Resource<Nothing>()
    data class Fail(val failMessage: String) : Resource<Nothing>()
    object Loading : Resource<Nothing>()
}
