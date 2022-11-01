package com.siele.unitconverter.repository

import com.siele.unitconverter.data.api.ConverterApi
import com.siele.unitconverter.data.model.ConversionResponse
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
import org.mockito.MockitoAnnotations
import retrofit2.Response

@RunWith(JUnit4::class)
class RepositoryTest {
    lateinit var defaultConverterRepository: DefaultConverterRepository

    @Mock
    lateinit var converterApi: ConverterApi

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        defaultConverterRepository = DefaultConverterRepository(converterApi)
    }

    @Test
    fun `get converted value test`() {
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
            Mockito.`when`(converterApi.getValue()).thenReturn(Response.success(conversionResponse))
            val response = defaultConverterRepository.getConvertedValue("", "", "")
            assertThat(conversionResponse, IsEqual(response.data))
        }
    }
}