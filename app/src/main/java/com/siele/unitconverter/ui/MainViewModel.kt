package com.siele.unitconverter.ui

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siele.unitconverter.data.model.ConversionResponse
import com.siele.unitconverter.data.repository.ConverterRepo
import com.siele.unitconverter.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val converterRepo: ConverterRepo) :ViewModel(){

    private val _responseState = MutableLiveData<Resource<ConversionResponse>>(Resource.Loading())
    val responseState: LiveData<Resource<ConversionResponse>> = _responseState

    @SuppressLint("SuspiciousIndentation")
    fun getValue(fromValue:String, fromType:String, toType:String){
        viewModelScope.launch {
           val response = converterRepo.getConvertedValue(fromValue, fromType, toType)
                _responseState.postValue(handleGetValueResponse(response))
        }
    }

    private fun handleGetValueResponse(response: Response<ConversionResponse>): Resource<ConversionResponse>?{
        return when {
            response.isSuccessful -> {
                response.body()?.let { resultResponse ->
                    Resource.Success(resultResponse)
                }
            }
            else -> {
                Resource.Error(response.errorBody().toString())
            }
        }
    }
}