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
    val packageName: String,
    val description: String,
    val threatTypesMask: Int,
    val email: String,
    val phone: String,
    val howBlockInfo: String,
    val howRestoreInfo: String)

@Entity
data class InstalledMortaAppDb(
    @PrimaryKey
    val packageName: String,
    val applicationName: String,
    val description: String,
    val threatTypesMask: Int,
    val threatTypes: String,
    val severityLevel: Int,
    val email: String,
    val phone: String,
    val howBlockInfo: String,
    val howRestoreInfo: String)

@Entity
data class InstalledAppDb(
    @PrimaryKey
    val packageName: String,
    val applicationName: String)


@Entity
data class ApplicationDataVersion(
    @PrimaryKey
    val version: Int)
