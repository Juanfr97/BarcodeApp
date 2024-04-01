package com.example.barcodeapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcodeapp.domain.models.Producto
import com.example.barcodeapp.domain.repositories.ProductRepository
import com.example.barcodeapp.domain.use_cases.GetProducts
import com.example.barcodeapp.presentation.states.ProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getProducts: GetProducts
) : ViewModel() {
    private var getProductsJob: Job?=null
    private val _state = MutableStateFlow(ProductsState())
    val state = _state.asStateFlow()

    init {
        getProductss()
    }

    fun getProductss(){
        getProductsJob?.cancel()
        getProductsJob = getProducts().onEach { result ->
            _state.value = ProductsState(products = result)
        }.launchIn(viewModelScope)
    }
}
