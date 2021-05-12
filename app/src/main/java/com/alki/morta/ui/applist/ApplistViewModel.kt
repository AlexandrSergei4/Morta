package com.alki.morta.ui.applist

import android.app.Application
import androidx.lifecycle.*
import com.alki.morta.domain.AppRepository
import com.alki.morta.domain.MortaApp
import kotlinx.coroutines.launch

class ApplistViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = AppRepository(application.applicationContext)
    val appList  = Transformations.map(repository.installedMortaApps)
    {
        it.map {
            MortaApp(
                it.activityName,
                it.applicationName,
                it.description,
                it.threatTypes,
                it.severityLevel,
                it.email,
                it.phone,
                it.howBlockInfo
            )
        }
    }

    init{
        viewModelScope.launch {
            repository.refresh()
        }
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