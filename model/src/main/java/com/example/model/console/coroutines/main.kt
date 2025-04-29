package com.example.model.console.coroutines

import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.delay
import kotlinx.coroutines.isActive
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking

fun main() = runBlocking {

    val job = launch {
        repeat(10) { index ->
            println("operation number $index")
            try {
                if(isActive){
                    delay(100)
                }
            } catch (exception: CancellationException) {
                println("CancellationException was thrown")
                throw CancellationException()
            }
        }
    }

    delay(250)
    println("Cancelling Coroutine")
    job.cancel()
}