package com.alki.morta.network

import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class MortaAppSensitiveDto(
        val activityName: String,
        val description: String,
        val threatTypes: String,
        val severityLevel: Int,
        val email: String,
        val phone: String,
        val howBlockInfo: String
)
