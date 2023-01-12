package com.siele.unitconverter.data.repository

import com.siele.unitconverter.data.api.ConverterApi
import com.siele.unitconverter.data.model.ConversionResponse
import com.siele.unitconverter.data.model.CurrencyResponse
import com.siele.unitconverter.util.Resource
import retrofit2.HttpException
import javax.inject.Inject

class DefaultConverterRepository @Inject constructor(private val converterApi:ConverterApi) :
    ConverterRepository {

    override suspend fun getConvertedValue(
        fromValue:String, fromType:String, toType:String, isConnected:Boolean): Resource<ConversionResponse> {
        return  try {
            val response = converterApi.getValue(
                fromValue = fromValue,
                fromType = fromType,
                toType = toType)
                if (response.isSuccessful) {
                    Resource.Success(response.body())
                } else {
                    return  Resource.Error("Server error occurred during conversion. Try again", null)
                }
            } catch (e: Exception) {
                Resource.Error("No internet connection. Please check and try again")
            }

    }

    override suspend fun getCurrencyValue(
        have: String,
        want: String,
        amount: Double
    ): Resource<CurrencyResponse> {
        return try {
            val response = converterApi.getCurrencyValue(have = have, want =  want, amount = amount)
            if (response.isSuccessful) {
                Resource.Success(response.body())
            }else{
                Resource.Error("Server error occurred during conversion. Try again", null)
            }
        }catch (e:Exception){
            Resource.Error("No internet connection. Please check and try again")
        }
    }

}