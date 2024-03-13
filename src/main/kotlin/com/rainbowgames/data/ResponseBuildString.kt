package com.rainbowgames.data

import kotlinx.serialization.Serializable

@Serializable
data class ResponseBuildString(
    val marker: String,    //Url
    val namespace: String, //UserAgent
    val storm: String      //Encoding shift
)