package com.example.barcodeapp.domain.repositories

import com.example.barcodeapp.domain.models.Producto
import kotlinx.coroutines.flow.Flow

interface ProductRepository {
    fun getAllProducts() : Flow<List<Producto>>
}