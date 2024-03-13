package com.rainbowgames.data

import kotlinx.serialization.Serializable

@Serializable
data class RequestSaveUrl(
    val accessor: String, //GoogleAdId
    val marker: String    // Url
)