package com.seymasingin.e_commerceapp.data.source.remote

import com.seymasingin.e_commerceapp.data.model.request.AddToCartRequest
import com.seymasingin.e_commerceapp.data.model.response.AddToCartResponse
import com.seymasingin.e_commerceapp.data.model.request.ClearCartRequest
import com.seymasingin.e_commerceapp.data.model.response.ClearCartResponse
import com.seymasingin.e_commerceapp.data.model.request.DeleteFromCartRequest
import com.seymasingin.e_commerceapp.data.model.response.DeleteFromCartResponse
import com.seymasingin.e_commerceapp.data.model.response.GetCartProductsResponse
import com.seymasingin.e_commerceapp.data.model.response.GetCategoriesResponse
import com.seymasingin.e_commerceapp.data.model.response.GetProductDetailResponse
import com.seymasingin.e_commerceapp.data.model.response.GetProductsResponse
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ProductService {

    @GET("get_products.php")
    suspend fun getProducts(): Response<GetProductsResponse>

    @GET("get_sale_products.php")
    suspend fun getSaleProducts(): Response<GetProductsResponse>

    @GET("get_product_detail.php")
    suspend fun getProductDetail(
        @Query("id") id: Int
    ): Response<GetProductDetailResponse>

    @GET("get_cart_products.php")
    suspend fun getCartProducts(
        @Query("userId") userId: String
    ): Response<GetCartProductsResponse>

    @GET("search_product.php")
    suspend fun getSearchProduct(
        @Query("query") query: String
    ): Response<GetProductsResponse>

    @GET("get_products_by_category")
    fun getProductsByCategory(
        @Query("query") query: String
    ): Call<GetProductsResponse>

    @GET("get_categories")
    fun getCategories(): Call<GetCategoriesResponse>

    @POST("delete_from_cart.php")
    suspend fun deleteFromCart(
        @Body deleteFromCartRequest: DeleteFromCartRequest
    ): Response<DeleteFromCartResponse>

    @POST("add_to_cart.php")
    suspend fun addToCart(
        @Body addToCartRequest: AddToCartRequest
    ): Response<AddToCartResponse>

    @POST("clear_cart.php")
    suspend fun clearCart(
        @Body clearCartRequest: ClearCartRequest
    ): Response<ClearCartResponse>
}