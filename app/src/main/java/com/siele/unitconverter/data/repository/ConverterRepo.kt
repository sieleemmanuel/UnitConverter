package com.siele.unitconverter.data.repository

import com.siele.unitconverter.data.api.ConverterApi
import javax.inject.Inject

class ConverterRepo @Inject constructor(private val converterApi:ConverterApi) {
    suspend fun getConvertedValue(
        fromValue:String, fromType:String, toType:String) =
        converterApi.getValue(fromValue = fromValue, fromType = fromType, toType = toType)

}