package com.rainbowgames.plugins

import com.rainbowgames.routes.addUrl
import com.rainbowgames.routes.createTempUrl
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        createTempUrl()
        addUrl()
    }
}
