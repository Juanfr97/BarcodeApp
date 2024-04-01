package com.example.barcodeapp.domain.use_cases

import com.example.barcodeapp.domain.models.Producto
import com.example.barcodeapp.domain.repositories.ProductRepository
import kotlinx.coroutines.flow.Flow


class GetProducts(
    private val repository: ProductRepository
) {
    operator fun invoke(): Flow<List<Producto>> {
        return repository.getAllProducts()
    }
}