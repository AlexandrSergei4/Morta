package com.alki.morta.domain

import android.content.Context
import android.content.Intent
import android.util.Log
import android.widget.Toast
import androidx.room.Transaction
import com.alki.morta.db.*
import com.alki.morta.network.Remote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import retrofit2.HttpException
import timber.log.Timber
import java.io.IOException
import java.lang.RuntimeException

class AppRepository(private val context: Context) {
    private val database = getDatabase(context)
    private val packageManager = context.packageManager;
    private val getAppsIntent = Intent(Intent.ACTION_MAIN)
        .apply {
            addCategory(Intent.CATEGORY_LAUNCHER)
        }

    /**
     * Установленные приложения с личными данными
     */
    val installedMortaApps = database.installedMortaAppsDao.getAll()
    /**
     * Все установленные приложения
     */
    val installedApps = database.installedAppsDao.getAllLiveData()

    suspend fun refresh() {
        Timber.d("Refreshing repository")
        withContext(Dispatchers.IO) {
            refreshFromInternet()
            refreshLocalApps()
        }
    }

    /**
     * Получение данных с сервера
     */
    @Transaction
    private suspend fun refreshFromInternet() {
        val currentDataVersion = database.versionDao.getCurrentVersion()
        try {
            val remoteVersion = Remote.mortaService.getAppVersion()
            if (currentDataVersion < remoteVersion) {
                val threatTypesDto = Remote.mortaService.getThreatTypes();
                val threatTypes = threatTypesDto.map {
                    ThreatTypeDb(
                        it.mask,
                        it.localizedName,
                        it.severityLevel
                    )
                }
                database.threatTypesDao.insertAll(threatTypes)
                val mortaApps = Remote.mortaService.getMortaApps().map {
                    MortaAppDb(
                        it.activityName,
                        it.description,
                        it.threatTypeMask,
                        it.email,
                        it.phone,
                        it.howBlockInfo,
                        it.howRestoreInfo
                    )
                }
                database.mortaAppsDao.insertAll(mortaApps)
                database.versionDao.insert(ApplicationDataVersion(remoteVersion))
            }
        } catch (e: IOException) {
            Toast.makeText(
                context, "Не удается получить данные. Проверьте подключение к интернету.",
                Toast.LENGTH_LONG
            ).show()
        } catch (e: HttpException) {
            Toast.makeText(
                context,
                "Не удалось получить данные. Проверьте подключение к интернету.",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    /**
     * Получение установленных приложений
     */
    private fun refreshLocalApps(){
        database.installedMortaAppsDao.clear()
        database.installedAppsDao.clear()
        val installedApps = packageManager.queryIntentActivities(getAppsIntent, 0)
            .mapNotNull {
                InstalledAppDb(
                    it.activityInfo.packageName,
                    packageManager.getApplicationInfo(it.activityInfo.packageName, 0)
                        .loadLabel(packageManager).toString()
                )
            }
        database.installedAppsDao.insertAll(installedApps)

        val threatTypesMap = database.threatTypesDao.getAll().map{
            it.mask to ThreatType(it.threatName, it.severityLevel)
        }.toMap()

        val installedAppsMap = installedApps.map { it.packageName to it }.toMap();
        val installedMortaApps = database.mortaAppsDao.getAllIn(installedApps.map { it.packageName }).map {
            var threatAndSeverity = threatMaskToThreatsAndSeverity(it.threatTypesMask, threatTypesMap)
            InstalledMortaAppDb(
                it.packageName,
                installedAppsMap[it.packageName]?.applicationName!!,
                it.description,
                it.threatTypesMask,
                threatAndSeverity.threat,
                threatAndSeverity.severity,
                it.email,
                it.phone,
                it.howBlockInfo,
                it.howRestoreInfo
            )
        }
        database.installedMortaAppsDao.insertAll(installedMortaApps)

    }

    private fun threatMaskToThreatsAndSeverity(
        mask: Int,
        map: Map<Int, ThreatType>
    ): ThreatsAndSeverity {
        var result = ThreatsAndSeverity("", 0)
        map.keys.forEach {
            if (it and mask == it) {
                if (result.threat.isNotBlank()) result.threat += ", "
                result.threat += map[it]!!.threatName
                result.severity += map[it]!!.severityLevel
            }
        }
        return result
    }

    private class ThreatsAndSeverity(
        var threat: String,
        var severity: Int
    )
}