package com.example.barcodeapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.barcodeapp.domain.use_cases.GetProducts
import com.example.barcodeapp.presentation.events.ProductsEvent
import com.example.barcodeapp.presentation.states.ProductsState
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainActivityViewModel @Inject constructor(
    private val getProducts: GetProducts
) : ViewModel() {
    private val _state = MutableStateFlow(ProductsState())
    val state = _state.asStateFlow()
    private val _uiEvent = Channel<String>()
    val uiEvent = _uiEvent.receiveAsFlow()

    init {
        loadProducts()
    }

    private fun loadProducts(){
        viewModelScope.launch {
            try {
                val products = getProducts() // Esto ahora es una llamada suspendida.
                _state.value = ProductsState(products = products)
            } catch (e: Exception) {
                // Aquí podrías manejar excepciones, como mostrar un error en la UI.
            }
        }
    }

    fun onEvent(event:ProductsEvent){
        when(event){
            is ProductsEvent.OnScan -> {
                event.content?.let { barcode ->
                    val product = _state.value.products.find { it.barcode == barcode }
                    if (product != null) {
                        val updatedShoppingCart = _state.value.shoppingCart.toMutableList().apply {
                            add(product)
                        }
                        _state.value = _state.value.copy(shoppingCart = updatedShoppingCart)
                    } else {
                        viewModelScope.launch {
                            _uiEvent.send("Producto no encontrado")
                        }
                    }
                }

            }
        }
    }
}
