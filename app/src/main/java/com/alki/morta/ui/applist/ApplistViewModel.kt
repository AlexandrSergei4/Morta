package com.alki.morta.ui.applist

import android.app.Application
import android.content.Intent
import androidx.lifecycle.*
import com.alki.morta.domain.AppRepository
import com.alki.morta.domain.MortaApp
import kotlinx.coroutines.launch

class ApplistViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository(application.applicationContext)
    private val packageManager = application.applicationContext.packageManager
    private var _appList :LiveData<List<MortaApp>>;
    val appList:LiveData<List<MortaApp>>
        get() = _appList

    init{
        val getAppsIntent = Intent(Intent.ACTION_MAIN)
            .apply {
                addCategory(Intent.CATEGORY_LAUNCHER)
            }
        val appsinfo = packageManager.queryIntentActivities(getAppsIntent, 0)
            .mapNotNull {
                it.activityInfo
            }.map { it.name to it }.toMap()
        _appList = repository.getInstalledMortaApps(appsinfo)
    }
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(ApplistViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return ApplistViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}