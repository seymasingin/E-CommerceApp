package com.seymasingin.e_commerceapp.data.mapper

import com.seymasingin.e_commerceapp.data.model.response.Product
import com.seymasingin.e_commerceapp.data.model.response.ProductListUI
import com.seymasingin.e_commerceapp.data.model.response.ProductUI

fun Product.mapToProductUI() =
    ProductUI(
        id = id ?: 1,
        title = title.orEmpty(),
        price = price ?: 0.0,
        salePrice = salePrice ?: 0.0,
        description = description.orEmpty(),
        category = category.orEmpty(),
        imageOne = imageOne.orEmpty(),
        imageTwo = imageTwo.orEmpty(),
        imageThree = imageThree.orEmpty(),
        rate = rate ?: 0.0,
        count = count ?: 0,
        saleState = saleState ?: false
    )

fun List<Product>.mapToProductListUI() =
    map {
        ProductListUI(
            id = it.id ?: 1,
            title = it.title.orEmpty(),
            price = it.price ?: 0.0,
            salePrice =  it.salePrice ?: 0.0,
            imageOne = it.imageOne.orEmpty(),
            category = it.category.orEmpty(),
            saleState = it.saleState ?: false
        )
    }