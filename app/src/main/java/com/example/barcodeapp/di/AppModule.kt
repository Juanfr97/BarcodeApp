package com.example.barcodeapp.di

import android.app.Application
import android.content.Context
import androidx.room.Room
import com.example.barcodeapp.data_access.BarcodeDb
import com.example.barcodeapp.data_access.dao.ProductDao
import com.example.barcodeapp.data_access.repositories.ProductRepositoryImpl
import com.example.barcodeapp.domain.repositories.ProductRepository
import com.example.barcodeapp.domain.use_cases.GetProducts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideString() : String = "Hola desde Dagger Hilt"

    @Provides
    @Singleton
    fun provideBarcodeDb(
        app: Application
    )= Room.databaseBuilder(
        app,
        BarcodeDb::class.java,
        "barcode_db"
    ).build()

    @Provides
    @Singleton
    fun provideProductRepository(db : BarcodeDb) : ProductRepository = ProductRepositoryImpl(db.productDao())

    @Provides
    @Singleton
    fun provideGetProducts(productRepository: ProductRepository) = GetProducts(productRepository)
}