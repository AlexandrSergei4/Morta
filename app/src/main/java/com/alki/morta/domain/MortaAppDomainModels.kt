package com.alki.morta.domain

import com.alki.morta.db.SensitiveAppDb
import com.alki.morta.threatStringToMask

data class MortaAppSensitive(
        val activityName: String,
        val applicationName:String,
        val description: String,
        val threatTypes: String,
        val severityLevel: Int,
        val email: String,
        val phone: String,
        val howBlockInfo: String)

fun MortaAppSensitive.asDatabaseModel():SensitiveAppDb{
        return SensitiveAppDb(
                activityName,
                applicationName,
                description,
                threatStringToMask(threatTypes),
                severityLevel,
                email,
                phone,
                howBlockInfo)
}

fun List<MortaAppSensitive>.asDatabaseModel():List<SensitiveAppDb>{
        return map {
                SensitiveAppDb(
                        it.activityName,
                        it.applicationName,
                        it.description,
                        threatStringToMask(it.threatTypes),
                        it.severityLevel,
                        it.email,
                        it.phone,
                        it.howBlockInfo
                )
        }
}
