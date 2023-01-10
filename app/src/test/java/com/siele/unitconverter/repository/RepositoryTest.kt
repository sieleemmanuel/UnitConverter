package com.siele.unitconverter.repository

import com.siele.unitconverter.data.api.ConverterApi
import com.siele.unitconverter.data.model.ConversionResponse
import com.siele.unitconverter.data.model.CurrencyResponse
import com.siele.unitconverter.data.repository.DefaultConverterRepository
import kotlinx.coroutines.runBlocking
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.IsEqual
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class RepositoryTest {
    lateinit var defaultConverterRepository: DefaultConverterRepository

    @Mock
    lateinit var converterApi: ConverterApi

    @Before
    fun setup() {
        MockitoAnnotations.openMocks(this)
        defaultConverterRepository = DefaultConverterRepository(converterApi)
    }

    @Test
    fun `get converted unit value test should return succcess response`() {
        runBlocking {
            val conversionResponse =
                ConversionResponse(
                    "USD",
                    "1",
                    "120",
                    120.00,
                    "KES",
                    true
                )
            `when`(converterApi.getValue()).thenReturn(Response.success(conversionResponse))
            val response = defaultConverterRepository.getConvertedValue("", "", "", true)
            assertThat(conversionResponse, IsEqual(response.data))
        }
    }

    @Test
    fun `get converted currency value should return success response`(){
        runBlocking {
            val currencyResponse = CurrencyResponse(
                "USD",
                1.0,
                123.05,
                "KES"
            )

            `when`(converterApi.getCurrencyValue("USD", "KES", 1.0))
                .thenReturn(Response.success(currencyResponse))

            val response  = defaultConverterRepository.getCurrencyValue("USD", "KES", 1.0)
            assertThat(currencyResponse.new_currency,IsEqual(response.data!!.new_currency))
        }
    }
}