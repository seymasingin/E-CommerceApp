package com.seymasingin.e_commerceapp.di

import com.google.firebase.auth.FirebaseAuth
import com.seymasingin.e_commerceapp.data.repository.ProductRepository
import com.seymasingin.e_commerceapp.data.repository.UserRepository
import com.seymasingin.e_commerceapp.data.source.local.ProductDao
import com.seymasingin.e_commerceapp.data.source.remote.ProductService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideRepository(productService: ProductService,
                          productDao: ProductDao) = ProductRepository(productService, productDao)

    @Provides
    @Singleton
    fun provideUserRepository(firebaseAuth: FirebaseAuth) = UserRepository(firebaseAuth)

}