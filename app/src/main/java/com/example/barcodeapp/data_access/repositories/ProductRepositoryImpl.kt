package com.example.barcodeapp.data_access.repositories

import com.example.barcodeapp.data_access.dao.ProductDao
import com.example.barcodeapp.domain.models.Product
import com.example.barcodeapp.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(
    private val productDao: ProductDao

) : ProductRepository {
    override  fun getAllProducts(): Flow<List<Product>> {
        return productDao.getAllProducts()
    }
}