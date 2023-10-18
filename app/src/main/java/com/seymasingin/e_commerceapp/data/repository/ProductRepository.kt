package com.seymasingin.e_commerceapp.data.repository

import android.util.Log
import androidx.lifecycle.MutableLiveData
import com.seymasingin.e_commerceapp.data.model.GetProductDetailResponse
import com.seymasingin.e_commerceapp.data.model.GetProductsResponse
import com.seymasingin.e_commerceapp.data.model.Product
import com.seymasingin.e_commerceapp.data.source.remote.ProductService
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class ProductRepository(private val productService: ProductService) {

    var productsList = MutableLiveData<List<Product>>()
    var saleProductsList = MutableLiveData<List<Product>>()

    fun getProducts() {
        productService.getProducts().enqueue(object : Callback<GetProductsResponse> {
                override fun onResponse(call: Call<GetProductsResponse>, response: Response<GetProductsResponse>) {
                    val result = response.body()

                    if (result?.status == 200) {
                        productsList.value = result.products.orEmpty()
                    } else {
                        //
                    }
                }

                override fun onFailure(call: Call<GetProductsResponse>, t: Throwable) {
                    Log.e("GetProducts", t.message.orEmpty())
                }
            })
    }

    fun getSaleProducts() {
        productService.getSaleProducts().enqueue(object : Callback<GetProductsResponse> {

                override fun onResponse(
                    call: Call<GetProductsResponse>,
                    response: Response<GetProductsResponse>
                ) {
                    val result = response.body()

                    if (result?.status == 200) {
                        saleProductsList.value = result.products.orEmpty()
                    } else {
                        //Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetProductsResponse>, t: Throwable) {
                    Log.e("GetSaleProducts", t.message.orEmpty())
                }
            })
    }

    fun getProductDetail(id: Int) {
        productService.getProductDetail(id).enqueue(object : Callback<GetProductDetailResponse> {

                override fun onResponse(call: Call<GetProductDetailResponse>, response: Response<GetProductDetailResponse>) {
                    val result = response.body()

                    if (result?.status == 200 && result.product != null) {
                        val product = result.product

                    } else {
                        //Toast.makeText(requireContext(), result?.message, Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<GetProductDetailResponse>, t: Throwable) {
                    Log.e("GetProductDetail", t.message.orEmpty())
                }
            })
    }
}