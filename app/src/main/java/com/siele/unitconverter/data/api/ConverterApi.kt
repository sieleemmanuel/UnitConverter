package com.siele.unitconverter.data.api

import com.siele.unitconverter.BuildConfig
import com.siele.unitconverter.data.model.ConversionResponse
import com.siele.unitconverter.data.model.CurrencyResponse
import com.siele.unitconverter.util.Constants
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query
import retrofit2.http.Url
import java.time.temporal.TemporalAmount

interface ConverterApi {
    @GET
    suspend fun getValue(
        @Url url: String = Constants.BASE_URL,
        @Query("user-id")
        userId:String = BuildConfig.USER_ID,
        @Query("api-key")
        apiKey:String = BuildConfig.NEUTRINO_API_PROD,
        @Query("from-value")
        fromValue:String ="",
        @Query("from-type")
        fromType:String ="",
        @Query("to-type")
        toType:String ="",
    ):Response<ConversionResponse>

    @GET
    suspend fun getCurrencyValue(@Url url: String = Constants.CURRENCY_BASE_URL,
                                 @Query ("have")
        have:String = "",
                                 @Query ("want")
        want:String = "",
                                 @Query ("amount")
        amount: Double
    ):Response<CurrencyResponse>
}