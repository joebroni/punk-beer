package com.corgrimm.punkbeer.di

import com.corgrimm.punkbeer.api.PunkApiService
import com.corgrimm.punkbeer.repository.PunkBeerRepo
import com.corgrimm.punkbeer.repository.PunkBeerRepoImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    const val PUNK_BASE_URL: String = "https://api.punkapi.com/v2/"

    @Provides
    fun provideBaseUrl() = PUNK_BASE_URL

    @Singleton
    @Provides
    fun provideOkHttpClient() =
        OkHttpClient
            .Builder()
            .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient, baseUrl: String): Retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .client(okHttpClient)
        .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): PunkApiService = retrofit.create(PunkApiService::class.java)

    @Provides
    fun providesPunkBeerRepo(
        punkApiService: PunkApiService,
    ): PunkBeerRepo = PunkBeerRepoImpl(
        punkBeerApi = punkApiService,
    )
}