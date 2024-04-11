package com.example.barcodeapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject


@HiltViewModel
class MainViewModel @Inject constructor(
    private val prueba : String
) : ViewModel()  {
    fun getString() = prueba
}
