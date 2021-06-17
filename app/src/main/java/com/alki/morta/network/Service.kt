package com.alki.morta.network

import retrofit2.http.GET

interface MortaAppService{
    @GET("/morta_apps")
    suspend fun getMortaApps() : List<MortaAppDto>

    @GET("/version")
    suspend fun getAppVersion() : Int

    @GET("/threat_types")
    suspend fun getThreatTypes() : List<ThreatTypeDto>
}
