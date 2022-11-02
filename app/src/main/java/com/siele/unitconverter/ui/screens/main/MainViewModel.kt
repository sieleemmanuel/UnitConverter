package com.siele.unitconverter.ui.screens.main

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
class MainViewModel @Inject constructor(private val converterRepository: ConverterRepository) :
    ViewModel() {

    private val _responseState: MutableLiveData<Resource<ConversionResponse>> = MutableLiveData()
    val responseState: LiveData<Resource<ConversionResponse>> = _responseState

    fun getValue(fromValue: String, fromType: String, toType: String, isConnected: Boolean) {
        viewModelScope.launch {
            val response =
                converterRepository.getConvertedValue(fromValue, fromType, toType, isConnected)
            _responseState.postValue(response)
        }
    }

}