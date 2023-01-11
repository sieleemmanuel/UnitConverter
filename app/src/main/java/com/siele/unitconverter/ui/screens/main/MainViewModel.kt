package com.siele.unitconverter.ui.screens.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.siele.unitconverter.data.model.ConversionResponse
import com.siele.unitconverter.data.model.CurrencyResponse
import com.siele.unitconverter.data.repository.ConverterRepository
import com.siele.unitconverter.util.Resource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(private val converterRepository: ConverterRepository) :
    ViewModel() {

    private val _responseState: MutableLiveData<Resource<ConversionResponse>> = MutableLiveData()
    val responseState: LiveData<Resource<ConversionResponse>> = _responseState

    private val _currencyResponseState: MutableLiveData<Resource<CurrencyResponse>> = MutableLiveData()
    val currencyResponseState: LiveData<Resource<CurrencyResponse>> = _currencyResponseState

    private val _unitValueEventChannel = Channel<Resource<ConversionResponse>>()
    val unitValueEvent = _unitValueEventChannel.receiveAsFlow()

    private val _currencyValueEventChannel = Channel<Resource<CurrencyResponse>>()
    val currencyValueEvent = _currencyValueEventChannel.receiveAsFlow()

    fun getValue(fromValue: String, fromType: String, toType: String, isConnected: Boolean) {
        viewModelScope.launch {
            val response =
                converterRepository.getConvertedValue(fromValue, fromType, toType, isConnected)
            _responseState.postValue(response)
        }
    }

    fun getCurrencyValue(have: String,  want: String, amount: Double) {
        viewModelScope.launch {
            val response = converterRepository.getCurrencyValue(have, want, amount)
            _currencyResponseState.postValue(response)
        }
    }

    fun triggerUnitValue(fromValue: String, fromType: String, toType: String, isConnected: Boolean) =
        viewModelScope.launch {
            _unitValueEventChannel.send(Resource.Loading())
            _unitValueEventChannel.send(converterRepository.getConvertedValue(fromValue, fromType, toType, isConnected))
        }

    fun triggerCurrencyValue(have: String,  want: String, amount: Double) =
        viewModelScope.launch {
            _currencyValueEventChannel.send(Resource.Loading())
            _currencyValueEventChannel.send(converterRepository.getCurrencyValue(have, want, amount))
        }

}