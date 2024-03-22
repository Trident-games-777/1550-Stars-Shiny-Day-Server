package com.rainbowgames

import com.rainbowgames.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

const val GADID_KEY = "fyvee7fg34"
const val DEEPLINK_KEY = "j3szvclsn7"
const val HOST = "classicstoryfromegypt.online"
const val SEGMENT = "ss2bumyv8.php"

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT")?.toInt() ?: 8080, module = Application::module)
    //embeddedServer(Netty, port = 8080, host = "192.168.0.102", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    configureSerialization()
    configureRouting()
    configureDatabase()
}
