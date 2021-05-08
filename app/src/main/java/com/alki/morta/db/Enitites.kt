package com.alki.morta.db

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.alki.morta.domain.MortaAppSensitive
import com.alki.morta.threatMaskToString

@Entity
data class ThreatType(
    @PrimaryKey
    val name: String,
    val severityLevel:Int)

@Entity
data class SensitiveAppDb(
    @PrimaryKey
    val activityName: String,
    val applicationName:String,
    val description: String,
    val threatTypesMask: Int,
    val severityLevel: Int,
    val email: String,
    val phone: String,
    val howBlockInfo: String)


fun List<SensitiveAppDb>.asDomainModel(): List<MortaAppSensitive> {
    return map {
        MortaAppSensitive(
            it.activityName,
            it.applicationName,
            it.description,
            threatMaskToString(it.threatTypesMask),
            it.severityLevel,
            it.email,
            it.phone,
            it.howBlockInfo
        )
    }
}

