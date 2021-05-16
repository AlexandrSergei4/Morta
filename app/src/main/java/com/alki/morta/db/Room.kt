package com.alki.morta.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface ThreatTypesDao {
    @Query("select * from ThreatTypeDb")
    fun getAllLiveData(): LiveData<List<ThreatTypeDb>>

    @Query("select * from ThreatTypeDb")
    fun getAll(): List<ThreatTypeDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(appList: List<ThreatTypeDb>)
}

@Dao
interface VersionDao {
    @Query("select max(version) from ApplicationDataVersion")
    fun getCurrentVersion():Int

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insert(version:ApplicationDataVersion)
}

@Dao
interface InstalledAppsDao {
    @Query("select * from InstalledAppDb")
    fun getAll():List<InstalledAppDb>

    @Query("select * from InstalledAppDb")
    fun getAllLiveData():LiveData<List<InstalledAppDb>>

    @Query("select * from InstalledAppDb where packageName = :packageName")
    fun getById(packageName: String):LiveData<InstalledAppDb>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(appList:InstalledAppDb)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(appList:List<InstalledAppDb>)

    @Query("delete from InstalledAppDb")
    fun clear()
}

@Dao
interface MortaAppsDao {
    @Query("select * from MortaAppDb")
    fun getAll():LiveData<List<MortaAppDb>>

    @Query("select * from MortaAppDb where packageName in (:packageNames)")
    fun getAllIn(packageNames:List<String>):List<MortaAppDb>

    @Query("select * from MortaAppDb where packageName = :packageName")
    fun getById(packageName:String):MortaAppDb?

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(mortaApp:MortaAppDb)

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(mortaApps:List<MortaAppDb>)
}

@Dao
interface InstalledMortaAppsDao {

    @Query("select * from InstalledMortaAppDb")
    fun getAll():LiveData<List<InstalledMortaAppDb>>

    @Query("select * from InstalledMortaAppDb where packageName in (:packageNames)")
    fun getAllIn(packageNames:List<String>):List<InstalledMortaAppDb>

    @Query("select * from InstalledMortaAppDb where packageName = :packageName")
    fun getById(packageName:String):LiveData<InstalledMortaAppDb>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(mortaAppList:List<InstalledMortaAppDb>)

    @Query("delete from InstalledMortaAppDb")
    fun clear()

}

@Database(entities = [MortaAppDb::class, InstalledMortaAppDb::class,
    ThreatTypeDb::class, ApplicationDataVersion::class,
    InstalledAppDb::class], exportSchema = false, version = 1)
abstract class MortaDatabase:RoomDatabase(){
    abstract val mortaAppsDao:MortaAppsDao
    abstract val installedMortaAppsDao:InstalledMortaAppsDao
    abstract val installedAppsDao:InstalledAppsDao
    abstract val threatTypesDao:ThreatTypesDao
    abstract val versionDao:VersionDao
}

private lateinit var INSTANCE:MortaDatabase

fun getDatabase(context: Context):MortaDatabase{
    synchronized(MortaDatabase::class.java)
    {
        if (!::INSTANCE.isInitialized)
        {
            INSTANCE = Room.databaseBuilder(context.applicationContext,
                MortaDatabase::class.java,
            "morta").build()
        }
        return INSTANCE
    }

}