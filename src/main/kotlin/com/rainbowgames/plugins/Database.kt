package com.rainbowgames.plugins

import com.mongodb.client.model.Filters
import com.mongodb.kotlin.client.coroutine.MongoClient
import com.mongodb.kotlin.client.coroutine.MongoCollection
import com.rainbowgames.HOST
import com.rainbowgames.data.RequestSaveUrl
import com.rainbowgames.data.ResponseSaveUrl
import com.rainbowgames.data.UrlEntity
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*
import kotlinx.coroutines.flow.firstOrNull
import org.bson.conversions.Bson

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

private const val COLLECTION_NAME = "urlsStarsShinyDayCollection"
private const val DB_NAME = "urlsStarsShinyDayCollectionDatabase"

fun Application.configureDatabase(){
    val connectionString =
        "mongodb+srv://yevhenmoiseiev:mais1208@cluster0.rilg0du.mongodb.net/?retryWrites=true&w=majority&appName=Cluster0"
    val client = MongoClient.create(connectionString = connectionString)
    val db = client.getDatabase(databaseName = DB_NAME)

    routing {
        post("/markers") {
            val requestSaveUrl = call.receive<RequestSaveUrl>()
            val googleAdId = requestSaveUrl.accessor.map { (it.code - 3).toChar() }.joinToString("")
            val collection: MongoCollection<UrlEntity> = db.getCollection<UrlEntity>(collectionName = COLLECTION_NAME)
            val queryParams: Bson = Filters.eq("googleAdId", googleAdId)
            val urlByGoogleAdId: UrlEntity? = collection.find<UrlEntity>(queryParams).firstOrNull()
            val url = requestSaveUrl.marker.map { (it.code - 3).toChar() }.joinToString("")


            if (googleAdId != url) {
                println("Url = $url")
                if (url.startsWith("https://$HOST")) {
                    call.respond(ResponseSaveUrl("Return"))
                } else {
                    if (urlByGoogleAdId == null) {
                        collection.insertOne(UrlEntity(googleAdId, url))
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

}