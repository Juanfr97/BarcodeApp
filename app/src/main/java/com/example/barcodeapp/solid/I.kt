package com.example.barcodeapp.solid

interface ICaminador {
    fun caminar()
}

interface INadador {
    fun nadar()
}

interface IVolador {
    fun volar()
}

class Perro : ICaminador {
    override fun caminar() {
        // caminar
    }
}
