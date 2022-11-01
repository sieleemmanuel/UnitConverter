package com.siele.unitconverter.ui.screens.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.siele.unitconverter.data.api.ConverterApi
import com.siele.unitconverter.data.model.ConversionResponse
import com.siele.unitconverter.data.repository.ConverterRepository
import com.siele.unitconverter.data.repository.DefaultConverterRepository
import com.siele.unitconverter.ui.getOrAwaitValue
import com.siele.unitconverter.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest{
    private lateinit var viewModel: MainViewModel
    private lateinit var converterRepository: ConverterRepository

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var conversionResponse:ConversionResponse

    @Mock
    lateinit var converterApi: ConverterApi

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        Dispatchers.setMain(testDispatcher)
        converterRepository = DefaultConverterRepository(converterApi)
        viewModel = MainViewModel(converterRepository)

        conversionResponse =
            ConversionResponse(
                "USD",
                "1",
                "120",
                120.00,
                "KES",
                true
            )
    }

    @Test
    fun getValue_InvalidInputs_returnError() {
        runBlocking {
            Mockito.`when`(converterRepository.getConvertedValue("", "USD", "KES"))
                .thenReturn(Resource.Error("An error occurred, An error occurred, enter a valid value", null))
            //viewModel.getValue("", "USD", "KES")
            viewModel._responseState.postValue(Resource.Error("An error occurred, enter a valid value", null))
            val response = viewModel.responseState.getOrAwaitValue()
            assertThat(response.message, IsEqual( Resource.Error("An error occurred, enter a valid value", null).message))
        }
    }

    @Test
    fun getValue_NetworkError_returnError() {
        runBlocking {
            Mockito.`when`(converterRepository.getConvertedValue("", "USD", "KES"))
                .thenReturn(Resource.Error("Network error, check your network connection", null))

            viewModel._responseState.postValue(Resource.Error("Network error, check your network connection", null))
            val response = viewModel.responseState.getOrAwaitValue()
            assertThat(response.message, IsEqual( Resource.Error("Network error, check your network connection", null).message))
        }
    }

    @Test
    fun getConvertedValue_IsValidRequest_returnSuccessResponse() {
        runBlocking {
            Mockito.`when`(converterRepository.getConvertedValue("1", "USD", "KES",))
                .thenReturn(Resource.Success(conversionResponse))

            viewModel._responseState.postValue(Resource.Success(conversionResponse))
            viewModel.getValue("1","USD", "KES" )

            val result = viewModel.responseState.getOrAwaitValue()
            assertThat(result.data, IsEqual( Resource.Success(conversionResponse).data))
        }
    }

}