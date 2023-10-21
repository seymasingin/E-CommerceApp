package com.seymasingin.e_commerceapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.data.model.GetProductDetailResponse
import com.seymasingin.e_commerceapp.data.model.GetProductsResponse
import com.seymasingin.e_commerceapp.data.model.Product
import com.seymasingin.e_commerceapp.data.source.remote.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(private val productService: ProductService) {

    var productsList = MutableLiveData<Resource<List<Product>>>()
    var saleProductsList = MutableLiveData<Resource<List<Product>>>()
    var searchProductsList = MutableLiveData<Resource<List<Product>>>()
    var productDetail = MutableLiveData<Resource<Product>>()
    var cartList = MutableLiveData<Resource<List<Product>>>()

    suspend fun getProducts() {
        productsList.value = Resource.Loading

        try {
            val response = productService.getProducts().body()

            productsList.value = if (response?.status == 200) {
                Resource.Success(response.products.orEmpty())
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            productsList.value = Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getSaleProducts() {
        saleProductsList.value = Resource.Loading

        try {
            val response = productService.getSaleProducts().body()

            saleProductsList.value = if (response?.status == 200) {
                Resource.Success(response.products.orEmpty())
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            saleProductsList.value = Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getSearchProducts(query: String) {
        if (query.length >= 3) {
            searchProductsList.value = Resource.Loading

            try {
                val response = productService.getSearchProduct(query).body()

                searchProductsList.value = if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty())
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                searchProductsList.value = Resource.Error(e.message.orEmpty())
            }
        }
    }

    suspend fun getProductDetail(id: Int) {
        productDetail.value = Resource.Loading

        try {
            val response = productService.getProductDetail(id).body()

            productDetail.value = if (response?.status == 200) {
                Resource.Success(response.product!!)
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            productDetail.value = Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun getCartProducts(userId: String) {
        cartList.value = Resource.Loading

        try {
            val response = productService.getCartProducts(userId).body()

            cartList.value = if (response?.status == 200) {
                Resource.Success(response.products.orEmpty())
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        } catch (e: Exception) {
            cartList.value = Resource.Error(e.message.orEmpty())
        }
    }

    suspend fun addToCart() {

    }
}