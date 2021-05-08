package com.alki.morta.ui.applist

import android.app.Application
import androidx.lifecycle.*
import com.alki.morta.db.getDatabase
import com.alki.morta.domain.AppRepository
import com.alki.morta.domain.MortaAppSensitive
import kotlinx.coroutines.launch

class ApplistViewModel(application: Application) : AndroidViewModel(application) {

    private val sensitiveAppRepository = AppRepository(application.applicationContext)
    private val _appList = sensitiveAppRepository.apps
    val appList:LiveData<List<MortaAppSensitive>>
        get() = _appList

    init{
        viewModelScope.launch {
            sensitiveAppRepository.refresh()
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