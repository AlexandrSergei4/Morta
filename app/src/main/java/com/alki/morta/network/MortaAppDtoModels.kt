package com.alki.morta.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MortaAppDto(
        val activityName: String,
        val description: String,
        val threatTypeMask: Int,
        val email: String,
        val phone: String,
        val howBlockInfo: String,
        val howRestoreInfo: String
)

@JsonClass(generateAdapter = true)
data class ThreatTypeDto(
        val mask: Int,
        val name: String,
        val localizedName: String,
        val severityLevel: Int
)
