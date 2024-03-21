package com.example.barcodeapp.solid

open class Calculadora {
    fun sumar(a: Int, b: Int): Int = a + b
    fun restar(a: Int, b: Int): Int = a - b
    // para agregar más funcionalidades, extendemos esta clase
}

class CalculadoraAvanzada : Calculadora() {
    fun multiplicar(a: Int, b: Int): Int = a * b
}
