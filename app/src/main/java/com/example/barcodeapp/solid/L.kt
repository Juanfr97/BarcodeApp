package com.example.barcodeapp.solid

open class Figura {
    open fun calcularArea(): Double = 0.0
}

class Circulo(private val radio: Double) : Figura() {
    override fun calcularArea(): Double = Math.PI * radio * radio
}

class Rectangulo(private val ancho: Double, private val alto: Double) : Figura() {
    override fun calcularArea(): Double = ancho * alto
}
