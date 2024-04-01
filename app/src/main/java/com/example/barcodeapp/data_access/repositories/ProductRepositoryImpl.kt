package com.example.barcodeapp.data_access.repositories

import com.example.barcodeapp.data_access.dao.ProductDao
import com.example.barcodeapp.domain.models.Producto
import com.example.barcodeapp.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao

) : ProductRepository {
    override  fun getAllProducts(): Flow<List<Producto>> {
        return productDao.getAllProducts()
    }
}