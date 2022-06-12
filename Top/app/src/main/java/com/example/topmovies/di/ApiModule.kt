package com.example.topmovies.di

import com.example.topmovies.api.ApiServices
import com.example.topmovies.utils.Constants
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object ApiModule {

    //Baseurl
    @Provides
    @Singleton
    fun provideBaseUrl() = Constants.BASE_URL

    //Connection time
    @Provides
    @Singleton
    fun provideConnectionTime() = Constants.CONNECTION_TIME

    //Gson
    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().setLenient().create()

    //HttpIntercpter
    @Provides
    @Singleton
    fun provideBodyInterceptor() = HttpLoggingInterceptor().apply {
        level = HttpLoggingInterceptor.Level.BODY
    }

    //Client
    @Provides
    @Singleton
    fun provideClient(
        time: Long,
        interceptor: HttpLoggingInterceptor
    ) = OkHttpClient.Builder()
        .writeTimeout(time, TimeUnit.SECONDS)
        .readTimeout(time, TimeUnit.SECONDS)
        .connectTimeout(time, TimeUnit.SECONDS)
        .addInterceptor(interceptor)
        .build()

    //Retrufit -> Apiservices
    @Provides
    @Singleton
    fun provideRetrufit(
        baseUrl: String,
        gson: Gson,
        client: OkHttpClient
    ): ApiServices {
        return Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(client)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
            .create(ApiServices::class.java)
    }
}