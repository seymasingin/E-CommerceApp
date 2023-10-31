package com.seymasingin.e_commerceapp.data.model.response

data class GetProductsResponse(
    val products: List<Product>?
) :BaseResponse()