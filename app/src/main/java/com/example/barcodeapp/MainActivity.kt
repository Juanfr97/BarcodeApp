package com.example.barcodeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.Toast
import androidx.activity.result.ActivityResultCallback
import androidx.activity.result.ActivityResultLauncher
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.barcodeapp.adapters.ProductAdapter
import com.example.barcodeapp.models.Product
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.ScanContract
import com.journeyapps.barcodescanner.ScanOptions
class MainActivity : AppCompatActivity() {
    private lateinit var button : Button
    private lateinit var productsRecyclerView: RecyclerView
    private val products = mutableListOf<Product>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        button = findViewById(R.id.btn_scan)
        setUpRecyclerView()
        button.setOnClickListener {
            scanCode()
        }
    }

    private fun scanCode(){
        val options = ScanOptions()
        options.setPrompt("Escanee un código QR")
        options.setBeepEnabled(true)
        options.setOrientationLocked(false)
        barcodeLauncher.launch(options)
    }
    private fun setUpRecyclerView(){
        productsRecyclerView = findViewById(R.id.rv_products)
        productsRecyclerView.adapter = ProductAdapter(products)
        productsRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    private val barcodeLauncher = registerForActivityResult(ScanContract()) { result ->
        if (result.contents == null) {
            // El usuario cerró la cámara sin escanear nada.
            Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show()
        } else {
            // El usuario escaneó algo y el contenido del código se encuentra en result.contents
            val product = Product.products.find { it.barcode == result.contents }
            if(product != null){
                products.add(product)
                productsRecyclerView.adapter?.notifyDataSetChanged()
                Toast.makeText(this, "Escaneado: ${result.contents}", Toast.LENGTH_LONG).show()
            }
            else{
                Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_LONG).show()
            }

        }
    }
}