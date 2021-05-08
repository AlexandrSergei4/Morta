package com.alki.morta.domain

data class MortaApp(
        val activityName: String,
        val applicationName:String,
        val description: String,
        val mask:Int,
        val threatTypes: List<ThreatType>,
        val email: String,
        val phone: String,
        val howBlockInfo: String)

data class ThreatType(
        val threatName: String,
        val severityLevel:Int)
