package com.siele.unitconverter.data.repository

import com.siele.unitconverter.data.model.ConversionResponse
import com.siele.unitconverter.util.Resource

interface ConverterRepository {
    suspend fun getConvertedValue(
        fromValue: String, fromType: String, toType: String, isConnected:Boolean
    ): Resource<ConversionResponse>
}