package com.example.barcodeapp.coroutines

import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.async
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main(){
    //globalScope()
    newTopic("Constructores")
    //cLaunch()
    //cAsync()
    job()
    readln()
}

private const val SEPARATOR = "=============="

fun newTopic(topic:String){
    println("\n $SEPARATOR $topic $SEPARATOR")
}

// Global scope permite que se ejecuten las corrutinas mientras la aplicacion se encuentre en funcionamiento
fun globalScope(){
    newTopic("Global Scope")
    GlobalScope.launch {
        println("Mi corutina")
    }
}

/*
* Una funcion suspendida va a poder ser pausada mientras espera un resultado. Hilo podra usarse para otras tareas
* Solo pueden ser llamadas desde otra funcion suspendida o desde otra corrutina
* */
suspend fun suspendFun(){
    newTopic("Suspend Fun")
    delay(1000)
}

/*
* runBlocking crea un nuevo coroutine scope y bloquea el hilo actual hasta que todas las corrutinas
*  dentro de su bloque se hayan completado.
* Fines educativos y pruebas
* */
fun cRunBlocking(){
    newTopic("Run blocking")
    runBlocking {
        println("Iniciando")
        delay(1000)
        println("Run blocking")
        println("terminando")
    }
}

/*
* Se utiliza cuando no necesitas devolver ningun resultado
* Recopilar datos de un usuario
* */
fun cLaunch(){
    newTopic("Launch")
    runBlocking {
        launch {
            println("Recopilando datos")
            delay(5000)
            println("termine")
        }
    }
}

fun cAsync(){
    newTopic("Async")
    runBlocking {
        val result = async {
            println("Consultando db")
            delay(2000)
            "Resultado"
        }
        println("result: ${result.await()}")
    }
}
/*
* Util en
*Juegos o aplicaciones interactivas
* Aplicaciones con múltiples tareas simultáneas
* Operaciones de E/S
* */
fun job(){
    runBlocking {
        val job = launch {
            print("Iniciando trabajo")
            delay(4000)
            println("job...")
            println("Terinando")
        }
        println("Job: $job")

        delay(2100)
        println("IsActive: ${job.isActive}")
        println("IsCompleted: ${job.isCompleted}")
        println("IsCanceled: ${job.isCancelled}")

        //delay(2100)
        println("Tarea cancelada o interrumpida")
        job.cancel()

        println("IsActive: ${job.isActive}")
        println("IsCompleted: ${job.isCompleted}")
        println("IsCanceled: ${job.isCancelled}")
    }
}