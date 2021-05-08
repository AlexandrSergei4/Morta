package com.alki.morta.domain

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.alki.morta.db.SensitiveAppDb
import com.alki.morta.db.asDomainModel
import com.alki.morta.db.getDatabase
import com.alki.morta.network.Remote
import com.alki.morta.threatStringToMask
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import android.content.pm.PackageManager
import android.util.Log
import com.alki.morta.network.MortaAppSensitiveDto
import timber.log.Timber

class AppRepository(private val context: Context) {
    private val database = getDatabase(context)
    private val packageManager = context.packageManager;
    suspend fun refresh() {
        withContext(Dispatchers.IO) {
            val apps = Remote.mortaService.getSensitiveApps()
            val installedApps = ArrayList<SensitiveAppDb>()
            Log.d("REPOSITORY","REMOTE SIZE ${apps.size}")
            apps.iterator().forEach {
                try {
                    Log.d("PROCESSING", "ActivityName = ${it.activityName}")
                    installedApps.add(SensitiveAppDb(
                            it.activityName,
                            packageManager.getApplicationInfo(it.activityName, 0).loadLabel(packageManager).toString(),
                            it.description,
                            threatStringToMask(it.threatTypes),
                            it.severityLevel,
                            it.email,
                            it.phone,
                            it.howBlockInfo
                    ))
                } catch (e: PackageManager.NameNotFoundException) {
                    //noop
                    Log.e("PROCESSING", "Cant find activity ${e.localizedMessage}")
                }
            }
            Log.d("REPOSITORY","PROCESSED SIZE ${installedApps.size}")
            database.sensitiveAppDao.insertAll(installedApps)
            val dbsize = database.sensitiveAppDao.getAllSensitiveApp().value
            Timber.d("DB SIZE : $dbsize")
            Log.d("Repository", "DB SIZE : $dbsize")
        }
    }

    val apps: LiveData<List<MortaAppSensitive>> = Transformations.map(
            database.sensitiveAppDao.getAllSensitiveApp()
    ) { it.asDomainModel() }
}