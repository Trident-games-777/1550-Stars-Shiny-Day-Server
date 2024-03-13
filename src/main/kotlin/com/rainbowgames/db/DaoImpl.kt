package com.rainbowgames.db

import com.rainbowgames.data.UrlEntity
import com.rainbowgames.db.DatabaseSingleton.dbQuery
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.select

class UrlsDaoImpl : UrlsDao {
    private fun resultRowToUrls(row: ResultRow) = UrlEntity(
        id = row[UrlsTable.id],
        googleAdId = row[UrlsTable.googleAdId],
        url = row[UrlsTable.url],
    )

    override suspend fun getUrlByGoogleAdId(googleAdId: String) = dbQuery {
        UrlsTable.select { UrlsTable.googleAdId eq googleAdId }.map(::resultRowToUrls).singleOrNull()
    }

    override suspend fun addUrl(googleAdId: String, url: String): UrlEntity? = dbQuery {
        val insertStatement = UrlsTable.insert {
            it[UrlsTable.googleAdId] = googleAdId
            it[UrlsTable.url] = url
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToUrls)
    }
}

val urlsDao: UrlsDao = UrlsDaoImpl()