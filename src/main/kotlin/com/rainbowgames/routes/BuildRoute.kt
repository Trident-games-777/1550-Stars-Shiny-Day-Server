package com.rainbowgames.routes

import com.rainbowgames.DEEPLINK_KEY
import com.rainbowgames.GADID_KEY
import com.rainbowgames.HOST
import com.rainbowgames.SEGMENT
import com.rainbowgames.data.ResponseBuildString
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

//accessor = googleAdId
//container = deeplink
//direction = userAgent
//marker = url

fun Route.createTempUrl() {
    get("/temp") {
        val googleAdId = call.parameters["accessor"]
        val deeplink = call.parameters["container"]
        val userAgent = call.parameters["direction"]

        val decodedGadid = googleAdId?.decodeInput()
        val decodedDeeplink = deeplink?.decodeInput()
        val decodedUserAgent = userAgent?.decodeInput()

        val url = URLBuilder(
            protocol = URLProtocol.HTTPS,
            host = HOST,
            pathSegments = listOf(SEGMENT),
            parameters = parametersOf(
                name = GADID_KEY,
                value = decodedGadid.toString()
            ) + parametersOf(name = DEEPLINK_KEY, value = decodedDeeplink.toString())
        ).build().toString()

        val takeVersion = decodedUserAgent?.split(" ")?.firstOrNull { it.startsWith("Version/") }.toString() + " "
        val newUserAgent = decodedUserAgent?.replace("; wv", "")?.replace(takeVersion, "").toString()

        val encodingShift = (1..5).random()
        val word = buildString {
            repeat(encodingShift) {
                append(('a'..'z').random())
            }
        }
        val encodedResponseUrl = url.map { (it.code - encodingShift).toChar() }.joinToString("")
        val encodedResponseUserAgent = newUserAgent.map { (it.code - encodingShift).toChar() }.joinToString("")

        call.respond(
            HttpStatusCode.OK, ResponseBuildString(
                marker = encodedResponseUrl, namespace = encodedResponseUserAgent, storm = word
            )
        )
    }
}

private fun String?.decodeInput(): String? = this?.map { (it.code - 3).toChar() }?.joinToString("")

fun Route.settingsObject() {
    get("/miscellaneous") {
        call.respond(
            HttpStatusCode.OK,

            )
    }
}