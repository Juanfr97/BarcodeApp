package com.example.barcodeapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "products")
data class Producto(
    @PrimaryKey val id : Int,
    val name : String,
    val price : Double,
)