package com.example.barcodeapp.di

import android.content.Context
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.sqlite.db.SupportSQLiteDatabase
import com.example.barcodeapp.data.BarcodeDb
import com.example.barcodeapp.data.dao.ProductDao
import com.example.barcodeapp.data.repositories.ProductRepositoryImpl
import com.example.barcodeapp.domain.models.Product
import com.example.barcodeapp.domain.repositories.ProductRepository
import com.example.barcodeapp.domain.use_cases.GetProducts
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    fun provideString() : String {
        return "Hola desde dagger Hilt"
    }

    @Volatile
    private var INSTANCE: BarcodeDb? = null

    @Provides
    @Singleton
    fun provideBarcodeDb(
        @ApplicationContext context: Context
    ): BarcodeDb {
        return INSTANCE ?: synchronized(this) {
            val instance = INSTANCE
            if (instance != null) {
                instance
            } else {
                val callback = object : RoomDatabase.Callback() {
                    override fun onCreate(db: SupportSQLiteDatabase) {
                        super.onCreate(db)
                        CoroutineScope(Dispatchers.IO).launch {
                            val db = INSTANCE ?: return@launch  // Early return if db is somehow null
                            val productDao = db.productDao()
                            populateDatabase(productDao)
                        }
                    }

                    private suspend fun populateDatabase(productDao: ProductDao) {
                        val products = Product.products // Assuming Product.products is already defined
                        productDao.insertAll(products)
                    }
                }

                Room.databaseBuilder(
                    context.applicationContext,
                    BarcodeDb::class.java,
                    "barcode_db"
                ) // Should be avoided in production
                    .addCallback(callback)
                    .build().also {
                        INSTANCE = it
                    }
            }
        }
    }


    @Provides
    @Singleton
    fun provideProductRepository(db:BarcodeDb) : ProductRepository{
        return ProductRepositoryImpl(db.productDao())
    }

    @Provides
    @Singleton
    fun provideGetProducts(productRepository:ProductRepository) : GetProducts{
        return GetProducts(productRepository)
    }
}

