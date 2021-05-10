package com.alki.morta.db

import androidx.room.*

@Entity
data class ThreatTypeDb(
    @PrimaryKey
    val mask: Int,
    val threatName: String,
    val severityLevel:Int)

@Entity
data class MortaAppDb(
    @PrimaryKey
    val activityName: String,
    val description: String,
    val threatTypesMask: Int,
    val email: String,
    val phone: String,
    val howBlockInfo: String)

@Entity
data class MortaInstalledAppDb(
    @PrimaryKey
    val activityName: String,
    val applicationName: String,
    val description: String,
    val threatTypesMask: Int,
    val threatTypes: String,
    val severityLevel: Int,
    val email: String,
    val phone: String,
    val howBlockInfo: String)

@Entity
data class ApplicationDataVersion(
    @PrimaryKey
    val version: Int)
