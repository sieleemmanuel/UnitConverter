package com.siele.unitconverter.repository

import com.siele.unitconverter.data.model.ConversionResponse
import com.siele.unitconverter.data.repository.ConverterRepository
import com.siele.unitconverter.util.Resource

class FakeConverterRepository : ConverterRepository{
    var conversionResponse:ConversionResponse =
        ConversionResponse(
            "USD",
            "1",
            "120",
            120.00,
            "KES",
            true
    )
    var isError = false

    override suspend fun getConvertedValue(
        fromValue: String,
        fromType: String,
        toType: String
    ): Resource<ConversionResponse> {
        return if(isError) {
            Resource.Error("Error", null)
        }else{
            Resource.Success(conversionResponse)
        }
    }
}