package com.alki.morta.ui.installedapps

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alki.morta.domain.App
import com.alki.morta.domain.AppRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class InstalledAppsViewModel
@Inject constructor(
    repository: AppRepository
) : ViewModel() {

    private val _navigateToMortaAppDetail= MutableLiveData<String?>()
    val navigateToMortaAppDetail:LiveData<String?> get() = _navigateToMortaAppDetail


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
}