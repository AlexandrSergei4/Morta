package com.alki.morta.domain

import android.content.Context
import android.content.Intent
import android.widget.Toast
import androidx.room.Transaction
import com.alki.morta.db.*
import com.alki.morta.network.Remote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException

class AppRepository(private val context: Context) {
    private val database = getDatabase(context)
    private val packageManager = context.packageManager;
    val installedMortaApps = database.dao.getMortaInstalledApps()
    val installedApps = database.installedAppsDao.getAllLiveData()
    private val getAppsIntent = Intent(Intent.ACTION_MAIN)
        .apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

    suspend fun refresh() {

        withContext(Dispatchers.IO) {
            refreshFromInternet()
            refreshLocalApps()
        }
    }

    @Transaction
    private suspend fun refreshFromInternet(){
        val currentDataVersion = database.dao.getCurrentVersion();
        try {
            val remoteVersion = Remote.mortaService.getAppVersion()
            if (currentDataVersion < remoteVersion) {
                val threatTypes = Remote.mortaService.getThreatTypes().map {
                    ThreatTypeDb(
                        it.mask,
                        it.localizedName,
                        it.severityLevel
                    )
                }
                database.dao.insertThreatTypes(threatTypes)
                val mortaApps = Remote.mortaService.getSensitiveApps().map {
                    MortaAppDb(
                        it.activityName,
                        it.description,
                        it.threatTypeMask,
                        it.email,
                        it.phone,
                        it.howBlockInfo,
                    )
                }
                database.dao.insertMortaApps(mortaApps)
                database.dao.insertVersion(ApplicationDataVersion(remoteVersion))
            }
        } catch (e: HttpException) {
            Toast.makeText(
                context,
                "Не удалось получить данные, проверте подключение к интернету.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun refreshLocalApps(){
        database.dao.clearMortaInstalledApps()
        var installedApps = packageManager.queryIntentActivities(getAppsIntent, 0)
            .mapNotNull {
                InstalledAppDb(
                    it.activityInfo.packageName,
                    packageManager.getApplicationInfo(it.activityInfo.packageName, 0)
                        .loadLabel(packageManager).toString(),
                    it.iconResource
                )
            }
        database.installedAppsDao.insertAll(installedApps)
        val threatTypesMap = database.dao.getThreatTypes().map{
            it.mask to ThreatType(it.threatName, it.severityLevel)
        }.toMap()
        val installedAppsMap = installedApps.map { it.activityName to it }.toMap();
        val installedMortaApps = database.dao.getMortaAppsIn(installedApps.map { it.activityName }).map {
            var threatAndSeverity = threatMaskToThreatsAndSeverity(it.threatTypesMask, threatTypesMap)
            MortaInstalledAppDb(
                it.activityName,
                installedAppsMap[it.activityName]?.applicationName!!,
                it.description,
                it.threatTypesMask,
                threatAndSeverity.threat,
                threatAndSeverity.severity,
                it.email,
                it.phone,
                it.howBlockInfo
            )
        }
        database.dao.insertMortaInstalledApps(installedMortaApps)

    }

    private fun threatMaskToThreatsAndSeverity(mask:Int, map:Map<Int,ThreatType>):ThreatsAndSeverity{
        var result = ThreatsAndSeverity("",0)
        map.keys.forEach{
            if (it and mask == it) {
                if (result.threat.isNotBlank()) result.threat += ", "
                result.threat += map[it]!!.threatName
                result.severity += map[it]!!.severityLevel
            }
        }
        return result
    }
    private class ThreatsAndSeverity(
        var threat:String,
        var severity:Int)
}