package com.example.barcodeapp.data_access.dao

import androidx.room.Dao
import androidx.room.Query
import com.example.barcodeapp.domain.models.Producto
import kotlinx.coroutines.flow.Flow

@Dao
interface ProductDao {
    @Query("SELECT * FROM products")
    fun getAllProducts() : Flow<List<Producto>>
}