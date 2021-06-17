package com.alki.morta.ui.installedapps

import android.app.Application
import androidx.lifecycle.*
import com.alki.morta.domain.App
import com.alki.morta.domain.AppRepository
import timber.log.Timber
import javax.inject.Inject

class InstalledAppsViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigateToMortaAppDetail= MutableLiveData<String?>()
    val navigateToMortaAppDetail:LiveData<String?> get() = _navigateToMortaAppDetail

    @Inject
    lateinit var repository:AppRepository
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

    init {
        Timber.d("INIT")
        var i = 0;
        Timber.d("i = $i")

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