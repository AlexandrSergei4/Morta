package com.alki.morta.db

import androidx.room.*
import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

/**
 * Хранение типов личной информации
 */
@Entity
data class ThreatTypeDb(
    @PrimaryKey
    val mask: Int,
    val threatName: String,
    val severityLevel:Int)

/**
 * Хранение приложений содержащих личные данные
 */
@Entity
data class MortaAppDb(
    @PrimaryKey
    val packageName: String,
    val description: String,
    val threatTypesMask: Int,
    val link:String?,
    val email: String,
    val phone: String,
    val howBlockInfo: String,
    val howRestoreInfo: String)

val MIGR_MORTA_APP_DB_1_2 = "ALTER TABLE MortaAppDb ADD COLUMN link TEXT"
/**
 * Хранение установленных приложений содержащих личные данные
 */
@Entity
data class InstalledMortaAppDb(
    @PrimaryKey
    val packageName: String,
    val applicationName: String,
    val description: String,
    val threatTypesMask: Int,
    val threatTypes: String,
    val severityLevel: Int,
    val link:String?,
    val email: String,
    val phone: String,
    val howBlockInfo: String,
    val howRestoreInfo: String)

val MIGR_INSTALLED_MORTA_APP_1_2 = "ALTER TABLE InstalledMortaAppDb ADD COLUMN link TEXT"

/**
 * Хранение установленных приложений
 */
@Entity
data class InstalledAppDb(
    @PrimaryKey
    val packageName: String,
    val applicationName: String)

/**
 * Версия данных загружаемых с сервера
 */
@Entity
data class ApplicationDataVersion(
    @PrimaryKey
    val version: Int)
