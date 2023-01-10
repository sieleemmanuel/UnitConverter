package com.siele.unitconverter.ui.screens.main

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.siele.unitconverter.data.model.ConversionResponse
import com.siele.unitconverter.data.model.CurrencyResponse
import com.siele.unitconverter.data.repository.DefaultConverterRepository
import com.siele.unitconverter.ui.getOrAwaitValue
import com.siele.unitconverter.util.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.mockito.Mockito.`when`
import org.mockito.Mockito.mock
import org.mockito.MockitoAnnotations

@ExperimentalCoroutinesApi
class MainViewModelTest{
    private lateinit var viewModel: MainViewModel
    private lateinit var converterRepository: DefaultConverterRepository

    private val testDispatcher = StandardTestDispatcher()
    private lateinit var conversionResponse:ConversionResponse
    private lateinit var currencyResponse:CurrencyResponse

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        Dispatchers.setMain(testDispatcher)
        converterRepository = mock(DefaultConverterRepository::class.java)
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
        currencyResponse = CurrencyResponse(
            new_amount = 123.05,
            new_currency = "KES",
            old_currency = "USD",
            old_amount = 1.0
        )
    }

    @Test
    fun `get converted value with invalid inputs should return response error`() {
        runTest {
            `when`(converterRepository.getConvertedValue("", "USD", "KES", true))
                .thenReturn(Resource.Error("An error occurred, enter a valid value", null))
            viewModel.getValue("", "USD", "KES", true)
            testDispatcher.scheduler.advanceUntilIdle()
            val response = viewModel.responseState.getOrAwaitValue()
            assertThat(response.message, IsEqual( Resource.Error("An error occurred, enter a valid value", null).message))
        }
    }

    @Test
    fun `get converted value with no network should return network response error`() {
        runBlocking {
            `when`(converterRepository.getConvertedValue("1", "USD", "KES", false))
                .thenReturn(Resource.Error("Network error, check your network connection", null))
           viewModel.getValue("1", "USD", "KES", false)
            testDispatcher.scheduler.advanceUntilIdle()
            val response = viewModel.responseState.getOrAwaitValue()
            assertThat(response.message, IsEqual( Resource.Error("Network error, check your network connection", null).message))
        }
    }

    @Test
    fun `get converted value request should return success response`() {
        runTest {
            `when`(converterRepository.getConvertedValue("1", "USD", "KES",true))
                .thenReturn(Resource.Success(conversionResponse))
            viewModel.getValue("1","USD", "KES", true )
            testDispatcher.scheduler.advanceUntilIdle()
            val result = viewModel.responseState.getOrAwaitValue()
            assertThat(result.data, IsEqual(Resource.Success(conversionResponse).data))
        }
    }

    @Test
    fun `get converted currency value should return success response`() {
        runTest {
            `when`(converterRepository.getCurrencyValue("USD", "KES", 1.0))
                .thenReturn(Resource.Success(currencyResponse))
            viewModel.getCurrencyValue("USD", "KES", 1.0)
            testDispatcher.scheduler.advanceUntilIdle()
            val result =  viewModel.currencyResponseState.getOrAwaitValue()
            assertThat(result.data, IsEqual(Resource.Success(currencyResponse).data))
        }
    }

}