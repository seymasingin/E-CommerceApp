package com.seymasingin.e_commerceapp.data.model.response

data class GetProductDetailResponse(
    val product: Product?,
    val status: Int?,
    val message: String?
)

data class GetCategoriesResponse(
    val categories: List<String>,
    val status: Int?,
    val message: String?
)