package com.alki.morta.domain

import android.content.Context
import android.content.pm.ActivityInfo
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.room.Transaction
import com.alki.morta.network.Remote
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import com.alki.morta.db.*
import retrofit2.HttpException

class AppRepository(private val context: Context) {
    private val database = getDatabase(context)
    private val packageManager = context.packageManager;
    private val threatTypes = database.dao.getThreatTypes()

fun getInstalledMortaApps(activityInfos: Map<String, ActivityInfo>): LiveData<List<MortaApp>> {
    val threatTypesMap = threatTypes.value?.map { it.mask to ThreatType(it.threatName, it.severityLevel) }
    return Transformations.map(database.dao.getMortaAppsIn(activityInfos.map { it.key }))
    { list ->
        list.map {
            MortaApp(
                it.activityName,
                activityInfos.get(it.activityName)!!.loadLabel(packageManager).toString(),
                it.description,
                it.threatTypesMask,
                threatTypesMap?.filter { pair -> it.threatTypesMask and pair.first == pair.first }
                    ?.map { pair -> pair.second }!!,
                it.email,
                it.phone,
                it.howBlockInfo
            )
        }
    }
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
            Toast.makeText(
                context,
                "Проверка обновлений . . .",
                Toast.LENGTH_LONG
            ).show()
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
                        it.threatTypesMask,
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
        Toast.makeText(
            context,
            "Получение списка установленных приложений . . . ",
            Toast.LENGTH_LONG
        ).show()
//        val installedApps = packageManager.getInstalledApplications()

    }

}