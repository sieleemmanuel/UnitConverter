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
       return if (isConnected) {
            val response = converterApi.getValue(
                fromValue = fromValue,
                fromType = fromType,
                toType = toType)
             try {
                if (response.isSuccessful) {
                    response.body()?.let {
                        return  Resource.Success(it)
                    } ?: Resource.Error("Unknown error occurred", null)
                } else {
                    return  Resource.Error(response.message(), null)
                }

            } catch (e: Exception) {
                Resource.Error("Failed: ${e.message}", null)
            }
        }else{
            Resource.Error("No internet connection")
        }
    }

    override suspend fun getCurrencyValue(
        have: String,
        want: String,
        amount: Double
    ): Resource<CurrencyResponse> {
        return try {
            val response = converterApi.getCurrencyValue(have = have, want =  want, amount = amount)
            Resource.Success(response.body())
        }catch (e:Exception){
            Resource.Error(e.message)
        }catch (e:HttpException){
            Resource.Error("No internet connection")
        }
    }

}