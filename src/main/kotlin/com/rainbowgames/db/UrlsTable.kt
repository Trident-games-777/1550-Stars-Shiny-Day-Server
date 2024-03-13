package com.rainbowgames.db

import org.jetbrains.exposed.sql.Table

object UrlsTable : Table() {
    val id = integer("id").autoIncrement()
    val googleAdId = varchar("googleAdId", 128)
    val url = varchar("url", 1024)

    override val primaryKey = PrimaryKey(googleAdId)
}