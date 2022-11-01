package com.siele.unitconverter.ui.screens.main

import android.annotation.SuppressLint
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siele.unitconverter.data.model.ConversionResponse
import com.siele.unitconverter.data.repository.ConverterRepository
import com.siele.unitconverter.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val converterRepository: ConverterRepository) :ViewModel(){

    val _responseState = MutableLiveData<Resource<ConversionResponse>>(null)
    val responseState: LiveData<Resource<ConversionResponse>> = _responseState


    @SuppressLint("SuspiciousIndentation")
    fun getValue(fromValue:String, fromType:String, toType:String){
        viewModelScope.launch {
           val response = converterRepository.getConvertedValue(fromValue, fromType, toType)
                _responseState.postValue(response)
        }
    }

}