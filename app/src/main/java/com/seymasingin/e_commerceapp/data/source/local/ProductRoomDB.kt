package com.seymasingin.e_commerceapp.data.source.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.seymasingin.e_commerceapp.data.model.response.ProductEntity

@Database(entities = [ProductEntity:: class], version = 1)
abstract class ProductRoomDB: RoomDatabase() {

    abstract fun productsDao(): ProductDao
}