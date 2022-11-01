package com.siele.unitconverter.data.repository

import com.siele.unitconverter.data.api.ConverterApi
import com.siele.unitconverter.data.model.ConversionResponse
import com.siele.unitconverter.util.Resource
import javax.inject.Inject

class DefaultConverterRepository @Inject constructor(private val converterApi:ConverterApi) :
    ConverterRepository {

    override suspend fun getConvertedValue(
        fromValue:String, fromType:String, toType:String): Resource<ConversionResponse> {
        val response = converterApi.getValue(
            fromValue = fromValue,
            fromType = fromType,
            toType = toType)

        return try {
            if (response.isSuccessful){
              response.body()?.let { Resource.Success(it)
              } ?: Resource.Error("Unknown error occurred", null)
            }else{
                Resource.Error("Unknown error occurred", null)
            }
            Resource.Loading()

        }catch (e:Exception){
            Resource.Error("Server connection failed, Check your internet connection", null)
        }


    }

}