package com.seymasingin.e_commerceapp.data.model.response

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favorites")
data class FavProduct(@PrimaryKey(autoGenerate = true)
                       @ColumnInfo(name = "id")
                       var id: Int = 0,
                      @ColumnInfo(name="title")
                       var title : String,
                      @ColumnInfo(name="price")
                       var price : Double,
                      @ColumnInfo(name="category")
                       var category : String,
                      @ColumnInfo(name="imageOne")
                       var imageOne: String
)