package com.alki.morta.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.http.GET

interface MortaAppService{
    @GET("/morta_apps")
    suspend fun getMortaApps() : List<MortaAppDto>

    @GET("/version")
    suspend fun getAppVersion() : Int

    @GET("/threat_types")
    suspend fun getThreatTypes() : List<ThreatTypeDto>
}
