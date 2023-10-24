package com.seymasingin.e_commerceapp.data.model.response

data class GetCartProductsResponse(
    val products: List<Product>?,
    val status: Int?,
    val message: String?
)
