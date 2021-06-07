package com.alki.morta.inejction

import android.content.Context
import com.alki.morta.network.MortaAppService
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
@InstallIn(SingletonComponent::class)
object MortaModule {

    @Provides
    fun moshi():Moshi =
        Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

    @Provides
    fun mortaAppService(
        moshi: Moshi
    ): MortaAppService =
        Retrofit.Builder()
            .baseUrl("https://mortaback.herokuapp.com/")
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
            .create(MortaAppService::class.java)

}