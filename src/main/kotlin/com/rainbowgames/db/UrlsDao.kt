package com.rainbowgames.db

import com.rainbowgames.data.UrlEntity

interface UrlsDao {
    suspend fun getUrlByGoogleAdId(googleAdId: String): UrlEntity?
    suspend fun addUrl(googleAdId: String, url: String): UrlEntity?
}