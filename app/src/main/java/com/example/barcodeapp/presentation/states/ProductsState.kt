package com.example.barcodeapp.presentation.states

import com.example.barcodeapp.domain.models.Product

data class ProductsState (
    val products : List<Product> = emptyList(),
    val shoppingCart : List<Product> = emptyList()
)