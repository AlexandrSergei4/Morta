package com.alki.morta.domain

data class MortaApp(
        val packageName: String,
        val applicationName:String,
        val description: String,
        val threatTypes: String,
        val severityLevel: Int,
        val email: String,
        val phone: String,
        val howBlockInfo: String,
        val howRestoreInfo: String)

data class App(
        val packageName: String,
        val applicationName:String)

data class ThreatType(
        val threatName: String,
        val severityLevel:Int)
