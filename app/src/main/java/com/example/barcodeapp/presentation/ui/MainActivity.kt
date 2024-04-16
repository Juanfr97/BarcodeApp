package com.example.barcodeapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barcodeapp.R
import com.example.barcodeapp.domain.adapters.ProductAdapter
import com.example.barcodeapp.domain.models.Product
import com.example.barcodeapp.presentation.events.ProductsEvent
import com.example.barcodeapp.presentation.viewmodels.MainViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var button : Button
    private lateinit var productsRecyclerView: RecyclerView
    private val products = mutableListOf<Product>()
    private val mainViewModel : MainViewModel by viewModels()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.btn_scan)
        button.setOnClickListener {
            scanCode()
        }
        observeState()
    }

    private fun observeState(){
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED){
                mainViewModel.state.collect{
                    Log.i("Products:",it.products.toString())
                    productsRecyclerView = findViewById(R.id.rv_products)
                    productsRecyclerView.adapter = ProductAdapter(it.products)
                    productsRecyclerView.layoutManager = LinearLayoutManager(this@MainActivity,LinearLayoutManager.VERTICAL,false)
                }
            }
        }
    }

    private fun scanCode(){
        val options = ScanOptions()
        options.setPrompt("Escanee un codigo de barras")
        options.setBeepEnabled(true)
        options.setOrientationLocked(false)
        barcodeLauncher.launch(options)
    }

    private val barcodeLauncher = registerForActivityResult(ScanContract()){ result ->
        if(result.contents == null){
            // El usuario cerro la camara sin escanear nada
            Toast.makeText(this,"Cancelado",Toast.LENGTH_LONG).show()
        }
        else{
            mainViewModel.onEvent(ProductsEvent.OnScan(result.contents))
        }
    }
}