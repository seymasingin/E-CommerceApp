package com.seymasingin.e_commerceapp.data.model

data class GetProductDetailResponse(
    val product: Product?,
    val status: Int?,
    val message: String?
)