package com.rainbowgames.routes

import com.rainbowgames.data.Res
import io.ktor.client.*
import io.ktor.client.call.*
import io.ktor.client.engine.android.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.netty.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import java.net.InetSocketAddress
import java.net.Proxy

fun Route.build() {
    get("/temp") {
        val gadid = call.parameters["gadid"]
        val deeplink = call.parameters["deeplink"]
        val url = URLBuilder(
            protocol = URLProtocol.HTTPS,
            host = "domain.com",
            pathSegments = listOf("abc.php"),
            parameters = parametersOf(name = "gadid_key", value = gadid.toString()) +
                    parametersOf(name = "deeplink_key", value = deeplink.toString())
        ).build().toString()

        call.respond(
            HttpStatusCode.OK,
            Res(url)
        )
    }
}