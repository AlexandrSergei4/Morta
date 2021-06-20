package com.alki.morta.ui.mortaapps

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel
import com.alki.morta.domain.AppRepository
import com.alki.morta.domain.MortaApp
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class MortaAppsViewModel
@Inject constructor(
    repository: AppRepository
    ) : ViewModel() {

    private val _navigateToMortaAppDetail= MutableLiveData<String?>()
    val navigateToMortaAppDetail get() = _navigateToMortaAppDetail

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

}