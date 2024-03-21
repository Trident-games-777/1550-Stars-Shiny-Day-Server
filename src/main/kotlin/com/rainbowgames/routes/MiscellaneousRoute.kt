package com.rainbowgames.routes

import com.rainbowgames.data.MiscellaneousReport
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Route.miscellaneous() {
    get("/miscellaneous") {
        val miscellaneousAdb = "development_settings_enabled|adb_enabled".map { (it.code + 1).toChar() }.joinToString("")
        call.respond(
            HttpStatusCode.OK, MiscellaneousReport(
                dev = miscellaneousAdb, enableSetting = true, disableSetting = false
            )
        )
    }
}