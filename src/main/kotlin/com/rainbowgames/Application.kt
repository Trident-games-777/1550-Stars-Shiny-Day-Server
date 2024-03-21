package com.rainbowgames

import com.rainbowgames.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

const val GADID_KEY = "mu9wu3d9le"
const val DEEPLINK_KEY = "24e3i0p9lt"
const val HOST = "starsshinyday.quest"
const val SEGMENT = "wod31u.php"

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
