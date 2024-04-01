package com.example.barcodeapp.data_access

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.barcodeapp.data_access.dao.ProductDao
import com.example.barcodeapp.domain.models.Producto

@Database(entities = [Producto::class], version = 1)
abstract class BarcodeDb : RoomDatabase(){
    abstract fun productDao() : ProductDao
}