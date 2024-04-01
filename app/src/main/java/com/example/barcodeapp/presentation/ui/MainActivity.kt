package com.example.barcodeapp.presentation.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barcodeapp.R
import com.example.barcodeapp.domain.adapters.ProductAdapter
import com.example.barcodeapp.domain.models.Product
import com.example.barcodeapp.presentation.viewmodels.MainActivityViewModel
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private lateinit var button : Button
    private lateinit var productsRecyclerView: RecyclerView
    private val products = mutableListOf<Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.btn_scan)
        val mainViewModel : MainActivityViewModel by viewModels()
        val state = mainViewModel.state
        setUpRecyclerView()
        button.setOnClickListener {
            scanCode()
        }

        lifecycleScope.launchWhenStarted {
            state.collect { state ->
                val products = state.products
                Log.i("MainActivity","Products: $products")
            }
        }
    }

    private fun setUpRecyclerView(){
        productsRecyclerView = findViewById(R.id.rv_products)
        productsRecyclerView.adapter = ProductAdapter(products)
        productsRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
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
            // El usuario capturo un codigo de barras o un QR
            val product = Product.products.find { p-> p.barcode == result.contents }
            if(product != null){
                products.add(product)
                productsRecyclerView.adapter?.notifyDataSetChanged()
                Toast.makeText(this,"Escaneado: ${product.name}",Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this,"Producto no encontrado",Toast.LENGTH_LONG).show()
            }
        }
    }
}