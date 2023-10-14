package com.seymasingin.e_commerceapp.data.source.remote

import com.seymasingin.e_commerceapp.data.model.AddToCartRequest
import com.seymasingin.e_commerceapp.data.model.CRUDResponse
import com.seymasingin.e_commerceapp.data.model.GetProductDetailResponse
import com.seymasingin.e_commerceapp.data.model.GetProductsResponse
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {

    @GET("get_products.php")
    fun getProducts(): Call<GetProductsResponse>

    @GET("get_sale_products.php")
    fun getSaleProducts(): Call<GetProductsResponse>

    @GET("get_product_detail.php")
    fun getProductDetail(
        @Query("id") id: Int
    ): Call<GetProductDetailResponse>

    @GET("get_cart_products.php")
    fun getCartProducts(
        @Query("userId") userId: Int
    ): Call<GetProductsResponse>

    @GET ("search_product.php")
    fun getSearchProduct(
        @Query("query")  query: String
    ): Call<GetProductsResponse>

    /*@POST("delete_from_cart.php")  //TEKRAR BAK
    fun deleteFromCart(
        @Body request :
    ): Call<CRUDResponse>*/

    @POST("add_to_cart.php")
    fun addToCart(
        @Body addToCartRequest: AddToCartRequest
    ): Call<CRUDResponse>
}