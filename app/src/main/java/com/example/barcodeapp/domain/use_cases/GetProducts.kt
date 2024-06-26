package com.example.barcodeapp.domain.use_cases

import com.example.barcodeapp.domain.models.Product
import com.example.barcodeapp.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class GetProducts(
    private val repository: ProductRepository
) {
    suspend operator fun invoke() : List<Product> {
        return repository.getAllProducts()
    }
}