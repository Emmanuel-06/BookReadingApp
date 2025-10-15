package com.example.bookreaderapp

/**
 * You can edit, run, and share this code.
 * play.kotlinlang.org
 */
import kotlinx.coroutines.*

fun main(){

    runBlocking {
        println("Tomorrow's Weather forecast")
        val forecast: Deferred<String> = async {
            getWeatherForecast()
        }
        val temperature: Deferred<String> = async {
            getTemperature()
        }
        println("${forecast.await()}, ${temperature.await()}")
    }
    println("Don't forget to go out with an Umbrella")
}


suspend fun getWeatherForecast(): String {
    delay(3000)
    return "Rainy"
}

suspend fun getTemperature(): String {
    delay(1000)
    return "12\u00b0"
}