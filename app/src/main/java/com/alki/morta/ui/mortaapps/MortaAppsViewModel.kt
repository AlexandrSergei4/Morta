package com.alki.morta.ui.mortaapps

import android.app.Application
import android.util.Log
import androidx.lifecycle.*
import com.alki.morta.domain.AppRepository
import com.alki.morta.domain.MortaApp
import kotlinx.coroutines.launch

class MortaAppsViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigateToMortaAppDetail= MutableLiveData<String?>()
    val navigateToMortaAppDetail get() = _navigateToMortaAppDetail

    private val repository = AppRepository(application.applicationContext)
    val appList  = Transformations.map(repository.installedMortaApps)
    {
        it.map {
            MortaApp(
                it.packageName,
                it.applicationName,
                it.description,
                it.threatTypes,
                it.severityLevel,
                it.email,
                it.phone,
                it.howBlockInfo,
                it.howRestoreInfo
            )
        }
    }


    fun onClickAppItem(app_package:String){
        _navigateToMortaAppDetail.value = app_package
    }

    fun navigationDone(){
        _navigateToMortaAppDetail.value = null
    }
    class Factory(val app: Application) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(MortaAppsViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return MortaAppsViewModel(app) as T
            }
            throw IllegalArgumentException("Unable to construct viewmodel")
        }
    }

}