package com.alki.morta.ui.mortaapps

import android.app.Application
import androidx.lifecycle.*
import com.alki.morta.domain.AppRepository
import com.alki.morta.domain.MortaApp
import timber.log.Timber

//import javax.inject.Inject

class MortaAppsViewModel(application: Application) : AndroidViewModel(application) {

    private val _navigateToMortaAppDetail= MutableLiveData<String?>()
    val navigateToMortaAppDetail get() = _navigateToMortaAppDetail

//    @Inject
    lateinit var repository:AppRepository
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

    init {
        Timber.d("INIT MORTAAPP VIEWMODEl")
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