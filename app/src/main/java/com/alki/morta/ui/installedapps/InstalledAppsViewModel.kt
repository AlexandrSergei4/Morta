package com.alki.morta.ui.installedapps

import android.app.Application
import androidx.lifecycle.*
import com.alki.morta.domain.App
import com.alki.morta.domain.AppRepository

class InstalledAppsViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigateToMortaAppDetail= MutableLiveData<String?>()
    val navigateToMortaAppDetail:LiveData<String?> get() = _navigateToMortaAppDetail

    private val repository = AppRepository(application.applicationContext)
    private var _installedAppList  = Transformations.map(repository.installedApps)
    {
        it.map {
            App(
                it.packageName,
                it.applicationName
            )
        }
    }
    val installedAppList: LiveData<List<App>>
        get() = _installedAppList

    fun onClickAppItem(app_package:String){
        _navigateToMortaAppDetail.value = app_package
    }

    fun navigationDone(){
        _navigateToMortaAppDetail.value = null
    }
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