package com.alki.morta.ui.appdetail

import android.app.Application
import androidx.lifecycle.*
import com.alki.morta.db.getDatabase

class AppDetailViewModel(val app: Application, val packageName: String) : AndroidViewModel(app) {

    private val db = getDatabase(app.applicationContext)
    var hasSecurityInfo = MutableLiveData<Boolean>()
    val mortaAppDb = db.installedMortaAppsDao.getById(packageName)
    val installedAppDb = db.installedAppsDao.getById(packageName)
    val allThreatTypes = db.threatTypesDao.getAllLiveData()


    class Factory(val app: Application, private val packageName: String) : ViewModelProvider.Factory {
        override fun <T : ViewModel?> create(modelClass: Class<T>): T {
            if (modelClass.isAssignableFrom(AppDetailViewModel::class.java)) {
                @Suppress("UNCHECKED_CAST")
                return AppDetailViewModel(app,packageName) as T
            }
            throw IllegalArgumentException("Unable to construct AppDetailViewModel")
        }
    }
}
