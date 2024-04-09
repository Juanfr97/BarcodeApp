package com.example.barcodeapp.presentation.events

import com.example.barcodeapp.domain.models.Product

sealed class ProductsEvent {
    data class OnScan(val content : String?) : ProductsEvent()
}