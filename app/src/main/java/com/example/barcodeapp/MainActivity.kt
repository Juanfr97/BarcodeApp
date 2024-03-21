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
        IntentIntegrator(this).initiateScan()
    }
    private fun setUpRecyclerView(){
        productsRecyclerView = findViewById(R.id.rv_products)
        productsRecyclerView.adapter = ProductAdapter(products)
        productsRecyclerView.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Cancelado", Toast.LENGTH_LONG).show()
            } else {
                val barcode = result.contents
                val product = Product.products.find { it.barcode == barcode }
                if(product != null){
                    products.add(product)
                    productsRecyclerView.adapter?.notifyDataSetChanged()
                }
                else{
                    Toast.makeText(this, "Producto no encontrado", Toast.LENGTH_LONG).show()
                }

                Toast.makeText(this, "Codigo de barras: " + result.contents, Toast.LENGTH_LONG).show()
            }
        }
        else {
            super.onActivityResult(requestCode, resultCode, data)
        }
    }
}