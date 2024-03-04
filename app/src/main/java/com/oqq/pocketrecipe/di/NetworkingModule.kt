package com.oqq.pocketrecipe.di

import com.google.gson.GsonBuilder
import com.oqq.pocketrecipe.data.remote.ApiService
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkingModule = module {
    single { provideRetrofit() }
    single { provideApiService(get()) }
}

fun provideRetrofit():Retrofit{
    val gson = GsonBuilder().setLenient().create()
    return Retrofit.Builder()
        .baseUrl(MyApplication.url_local)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

fun provideApiService(retrofit:Retrofit): ApiService{
    return retrofit.create(ApiService::class.java)
}