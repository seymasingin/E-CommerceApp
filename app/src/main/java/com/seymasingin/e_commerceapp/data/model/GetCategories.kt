package com.seymasingin.e_commerceapp.data.model

data class GetCategories(
    val categories: List<String>,
    val status: Int?,
    val message: String?
)