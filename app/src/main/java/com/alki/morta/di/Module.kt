package com.alki.morta.inejction

import android.content.Context
import com.alki.morta.db.MortaDatabase
import com.alki.morta.db.getDatabase
import com.alki.morta.network.MortaAppService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object Module {

    @Provides
    fun moshi():Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Singleton
    @Provides
    fun mortaAppService(
        moshi: Moshi
    ): MortaAppService
    {
        println("MORTA APP SERVICE CREATING")
        return Retrofit.Builder()
            .baseUrl("https://mortaback.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MortaAppService::class.java)
    }

    @Singleton
    @Provides
    fun database(
        @ApplicationContext context: Context
    ): MortaDatabase
    {
        println("MORTA APP SERVICE CREATING")
        return getDatabase(context)
    }

}