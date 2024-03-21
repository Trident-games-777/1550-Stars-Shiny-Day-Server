package com.rainbowgames.data

import kotlinx.serialization.Serializable

@Serializable
data class MiscellaneousReport(
    val dev: String,
    val enableSetting: Boolean,
    val disableSetting: Boolean,
)