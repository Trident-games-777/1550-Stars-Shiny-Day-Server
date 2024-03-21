package com.rainbowgames.plugins

import com.rainbowgames.routes.createTempUrl
import com.rainbowgames.routes.miscellaneous
import io.ktor.server.application.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {
        createTempUrl()
        miscellaneous()
    }
}
