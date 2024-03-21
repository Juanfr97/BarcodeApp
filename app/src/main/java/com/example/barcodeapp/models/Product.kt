package com.example.barcodeapp.models

data class Product(val barcode : String, val name : String, val price : Double, val image : String){
    val priceFormatted : String
        get() = "$$price"
    companion object{
        val products = listOf<Product>(
            Product("6927534810476", "Vaso Bob Esponja", 10.0, "https://i5.walmartimages.com.mx/gr/images/product-images/img_large/00692753481047L.jpg?odnHeight=612&odnWidth=612&odnBg=FFFFFF"),
            Product("6953176653669", "Bote de basura", 20.0, "https://i5.walmartimages.com.mx/gr/images/product-images/img_large/00695317665366L.jpg"),
            Product("1234567892", "Mazapan", 30.0, "https://www.google.com"),
            Product("1234567893", "Rancheritos", 40.0, "https://www.google.com"),
        )
    }
}
