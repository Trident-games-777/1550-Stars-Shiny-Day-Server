package com.rainbowgames.routes

import com.rainbowgames.HOST
import com.rainbowgames.data.ResponseSaveUrl
import com.rainbowgames.data.RequestSaveUrl
import com.rainbowgames.db.urlsDao
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*


/**
 * When 'googleAdId == url' -> this is request for saved url for this googleAdId
 * When googleAdId != url -> this is request for saving new url for this googleAdId
 *
 * If new url was saved returns string "Done"
 * If attempt to save new url but this googleAdId already has url returns string "Cancel"
 * If attempt to save new url with domain returns "Return"
 *
 * If request for saved url but there is no saved url for this googleAdId returns "Nothing"
 * If request for saved url end url for this googleAdId is present returns url (charCode-number) + number at the end
 */
fun Route.addUrl() {
    post("/markers") {
        val requestSaveUrl = call.receive<RequestSaveUrl>()
        val googleAdId = requestSaveUrl.accessor.map { (it.code - 3).toChar() }.joinToString("")
        val urlByGoogleAdId = urlsDao.getUrlByGoogleAdId(googleAdId)
        val url = requestSaveUrl.marker.map { (it.code - 3).toChar() }.joinToString("")
        if (googleAdId != url) {
            if (url.startsWith("https://$HOST")) {
                call.respond(ResponseSaveUrl("Return"))
            } else {
                if (urlByGoogleAdId == null) {
                    urlsDao.addUrl(googleAdId = googleAdId, url = url)
                    call.respond(ResponseSaveUrl("Done"))
                } else {
                    call.respond(ResponseSaveUrl("Cancel"))
                }
            }
        } else {
            val minusKey = (1..5).random()
            val answer = if (urlByGoogleAdId == null) "Nothing" else {
                urlByGoogleAdId.url.map { (it.code - minusKey).toChar() }.joinToString("") + minusKey.toString()
            }
            call.respond(ResponseSaveUrl(answer))
        }
    }
}