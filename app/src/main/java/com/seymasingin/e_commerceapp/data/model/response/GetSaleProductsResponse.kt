package com.seymasingin.e_commerceapp.data.model.response

data class GetSaleProductsResponse(
    val products: List<Product>?,
): BaseResponse()
