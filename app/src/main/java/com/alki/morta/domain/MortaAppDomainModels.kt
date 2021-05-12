package com.alki.morta.domain

data class MortaApp(
        val activityName: String,
        val applicationName:String,
        val description: String,
        val threatTypes: String,
        val severityLevel: Int,
        val email: String,
        val phone: String,
        val howBlockInfo: String)

data class App(
        val activityName: String,
        val applicationName:String,
        val iconResource: Int)

data class ThreatType(
        val threatName: String,
        val severityLevel:Int)
