package com.alki.morta.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.room.*

@Dao
interface MortaDao{
    @Query("select * from MortaAppDb")
    fun getMortaApps():LiveData<List<MortaAppDb>>

    @Query("select * from MortaAppDb where activityName in (:activityNames)")
    fun getMortaAppsIn(activityNames:List<String>):List<MortaAppDb>

    @Query("select * from MortaInstalledAppDb")
    fun getMortaInstalledApps():LiveData<List<MortaInstalledAppDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMortaApps(appList:List<MortaAppDb>)

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertMortaInstalledApps(appList:List<MortaInstalledAppDb>)

    @Query("delete from MortaInstalledAppDb")
    fun clearMortaInstalledApps()

    @Query("select * from ThreatTypeDb")
    fun getThreatTypesLiveData():LiveData<List<ThreatTypeDb>>

    @Query("select * from ThreatTypeDb")
    fun getThreatTypes():List<ThreatTypeDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertThreatTypes(appList:List<ThreatTypeDb>)

    @Query("select max(version) from ApplicationDataVersion")
    fun getCurrentVersion():Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertVersion(version:ApplicationDataVersion)
}

@Database(entities = [MortaAppDb::class, MortaInstalledAppDb::class, ThreatTypeDb::class, ApplicationDataVersion::class], version = 1)
abstract class SensitiveAppDatabase:RoomDatabase(){
    abstract val dao:MortaDao
}

private lateinit var INSTANCE:SensitiveAppDatabase

fun getDatabase(context: Context):SensitiveAppDatabase{
    synchronized(SensitiveAppDatabase::class.java)
    {
        if (!::INSTANCE.isInitialized)
        {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
            SensitiveAppDatabase::class.java,
            "morta").build()
        }
        return INSTANCE
    }

}