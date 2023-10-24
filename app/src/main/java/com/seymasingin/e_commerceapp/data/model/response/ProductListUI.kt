package com.seymasingin.e_commerceapp.data.model.response

data class ProductListUI(
    val id: Int,
    val title: String,
    val price: Double,
    val salePrice: Double,
    val imageOne: String,
    val category: String,
    val saleState: Boolean
)
