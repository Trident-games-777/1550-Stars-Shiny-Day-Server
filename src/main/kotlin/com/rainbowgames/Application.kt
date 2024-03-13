package com.rainbowgames

import com.rainbowgames.db.DatabaseSingleton
import com.rainbowgames.plugins.*
import io.ktor.server.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*

const val GADID_KEY = "p2zyv27cym"
const val DEEPLINK_KEY = "yrm65qc34c"
const val HOST = "rainbowwheel.site"
const val SEGMENT = "pog2567.php"

fun main() {
    embeddedServer(Netty, port = System.getenv("PORT")?.toInt() ?: 8080, module = Application::module)
    //embeddedServer(Netty, port = 8080, host = "192.168.0.102", module = Application::module)
        .start(wait = true)
}

fun Application.module() {
    DatabaseSingleton.init()
    configureSerialization()
    configureMonitoring()
    configureRouting()
}
