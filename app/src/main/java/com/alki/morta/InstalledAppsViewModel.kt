package com.alki.morta

import android.app.Application
import androidx.lifecycle.*
import com.alki.morta.domain.App
import com.alki.morta.domain.AppRepository
import com.alki.morta.domain.MortaApp
import com.alki.morta.ui.applist.ApplistViewModel

class InstalledAppsViewModel(application: Application) : AndroidViewModel(application) {
    // TODO: Implement the ViewModel
    private val repository = AppRepository(application.applicationContext)
    private var _installedAppList  = Transformations.map(repository.installedApps)
    {
        it.map {
            App(
                it.activityName,
                it.applicationName,
                it.iconResource
            )
        }
    }
    val installedAppList: LiveData<List<App>>
        get() = _installedAppList

    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(InstalledAppsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return InstalledAppsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct InstalledAppsViewModel")
        }
    }
}