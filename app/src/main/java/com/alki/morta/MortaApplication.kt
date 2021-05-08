package com.alki.morta

import android.app.Application
import com.alki.morta.db.getDatabase
import com.alki.morta.domain.AppRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MortaApplication: Application() {

    private val applicationScope = CoroutineScope(Dispatchers.Default)

    override fun onCreate() {
        super.onCreate()
        delayedInit()
    }

    private fun delayedInit() {
        val repository = AppRepository(applicationContext)
        applicationScope.launch { repository.refresh() }
    }
}