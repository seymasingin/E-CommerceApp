package com.seymasingin.e_commerceapp.data.repository

import com.seymasingin.e_commerceapp.common.Resource
import com.seymasingin.e_commerceapp.data.mapper.mapProductEntityToProductUI
import com.seymasingin.e_commerceapp.data.mapper.mapProductToProductUI
import com.seymasingin.e_commerceapp.data.mapper.mapToProductEntity
import com.seymasingin.e_commerceapp.data.mapper.mapToProductUI
import com.seymasingin.e_commerceapp.data.model.request.AddToCartRequest
import com.seymasingin.e_commerceapp.data.model.request.ClearCartRequest
import com.seymasingin.e_commerceapp.data.model.request.DeleteFromCartRequest
import com.seymasingin.e_commerceapp.data.model.response.ProductUI
import com.seymasingin.e_commerceapp.data.source.local.ProductDao
import com.seymasingin.e_commerceapp.data.source.remote.ProductService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

class ProductRepository(private val productService: ProductService,
                        private val productDao: ProductDao
) {

    suspend fun getProducts(): Resource<List<ProductUI>> =
        withContext(Dispatchers.IO) {
            try {
                val favorites = productDao.getProductIds()
                val response = productService.getProducts().body()

                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapProductToProductUI(favorites))
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getSaleProducts(): Resource<List<ProductUI>>  =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getSaleProducts().body()
                val favorites = productDao.getProductIds()

                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapProductToProductUI(favorites))
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getSearchProducts(query: String): Resource<List<ProductUI>> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getSearchProduct(query).body()
                val favorites = productDao.getProductIds()

                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapProductToProductUI(favorites))
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getProductDetail(id: Int): Resource<ProductUI> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getProductDetail(id).body()
                val favorites = productDao.getProductIds()

                if (response?.status == 200 && response.product != null) {
                    Resource.Success(response.product.mapToProductUI(favorites))
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun getCartProducts(userId: String): Resource<List<ProductUI>> =
        withContext(Dispatchers.IO) {
            try {
                val response = productService.getCartProducts(userId).body()
                val favorites = productDao.getProductIds()

                if (response?.status == 200) {
                    Resource.Success(response.products.orEmpty().mapProductToProductUI(favorites))
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun addToCart(userId: String, productId: Int): Resource<String> =
        withContext(Dispatchers.IO) {
            val addToCartRequest = AddToCartRequest(userId, productId)
            val response = productService.addToCart(addToCartRequest).body()

            if (response?.status == 200) {
                Resource.Success(response.message.orEmpty())
            } else {
                Resource.Fail(response?.message.orEmpty())
            }
        }

    suspend fun deleteFromCart(id: Int): Resource<String> =
        withContext(Dispatchers.IO){
            try {
                val deleteFromCartRequest = DeleteFromCartRequest(id)
                val response = productService.deleteFromCart(deleteFromCartRequest).body()

                if (response?.status == 200) {
                    Resource.Success(response.message.orEmpty())
                } else {
                    Resource.Fail(response?.message.orEmpty())
                }
            }
            catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun clearCart(userId: String): Resource<String> =
        withContext(Dispatchers.IO){
            try {
                val clearCartRequest = ClearCartRequest(userId)
                val response = productService.clearCart(clearCartRequest).body()

                if(response?.status == 200){
                    Resource.Success(response.message.orEmpty())
                } else{
                    Resource.Fail(response?.message.orEmpty())
                }
            }
            catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }

    suspend fun addToFavorites(productUI: ProductUI) {
        productDao.addProduct(productUI.mapToProductEntity())
    }

    suspend fun deleteFromFavorites(productUI: ProductUI) {
        productDao.deleteProduct(productUI.mapToProductEntity())
    }

    suspend fun getFavorites(): Resource<List<ProductUI>> =
        withContext(Dispatchers.IO) {
            try {
                val products = productDao.getProducts()

                if (products.isEmpty()) {
                    Resource.Fail("Products not found")
                } else {
                    Resource.Success(products.mapProductEntityToProductUI())
                }
            } catch (e: Exception) {
                Resource.Error(e.message.orEmpty())
            }
        }
}