package com.siele.unitconverter.di

import com.siele.unitconverter.data.api.ConverterApi
import com.siele.unitconverter.data.api.RetrofitInstance
import com.siele.unitconverter.data.repository.ConverterRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Provides
    @Singleton
    fun provideApi():ConverterApi = RetrofitInstance.retrofit.create(ConverterApi::class.java)

    @Provides
    @Singleton
    fun provideRepository(api:ConverterApi):ConverterRepo = ConverterRepo(api)

}