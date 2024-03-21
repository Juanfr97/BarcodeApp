package com.example.barcodeapp.solid

interface IStorage {
    fun leer(): String
    fun escribir(informacion: String)
}

class SSD : IStorage {
    override fun leer(): String {
        return "Leyendo desde SSD"
    }

    override fun escribir(informacion: String) {
        println("Escribiendo '$informacion' en SSD")
    }
}

class HDD : IStorage {
    override fun leer(): String {
        return "Leyendo desde HDD"
    }

    override fun escribir(informacion: String) {
        println("Escribiendo '$informacion' en HDD")
    }
}

class Computadora(private val almacenamiento: IStorage) {
    fun guardarDatos(informacion: String) {
        almacenamiento.escribir(informacion)
    }

    fun recuperarDatos(): String {
        return almacenamiento.leer()
    }
}
