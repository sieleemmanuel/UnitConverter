package com.siele.unitconverter.data.api

import com.siele.unitconverter.BuildConfig
import com.siele.unitconverter.data.model.ConversionResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface ConverterApi {
    @GET("convert/")
    suspend fun getValue(
        @Query("user-id")
        userId:String = BuildConfig.USER_ID,
        @Query("api-key")
        apiKey:String = BuildConfig.NEUTRINO_API_PROD ,
        @Query("from-value")
        fromValue:String ="",
        @Query("from-type")
        fromType:String ="",
        @Query("to-type")
        toType:String ="",
    ):Response<ConversionResponse>
}