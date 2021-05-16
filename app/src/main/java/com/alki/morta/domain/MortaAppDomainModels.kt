package com.alki.morta.domain

/**
 * Приложение сожержащее личные данные
 */
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

/**
 * Установленное приложение
 */
data class App(
        val packageName: String,
        val applicationName:String)
/**
 * Тип личной информации
 */
data class ThreatType(
        val threatName: String,
        val severityLevel:Int)
