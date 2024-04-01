package com.example.barcodeapp.presentation.states

import com.example.barcodeapp.domain.models.Producto

data class ProductsState (
    val products : List<Producto> = emptyList()
)