package com.alki.morta.db

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.room.*

@Dao
interface SensitiveAppDao{
    @Query("select * from sensitiveappdb")
    fun getAllSensitiveApp():LiveData<List<SensitiveAppDb>>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(appList:List<SensitiveAppDb>)
}

@Database(entities = [SensitiveAppDb::class], version = 1)
abstract class SensitiveAppDatabase:RoomDatabase(){
    abstract val sensitiveAppDao:SensitiveAppDao
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