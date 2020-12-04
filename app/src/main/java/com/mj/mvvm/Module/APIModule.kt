package com.mj.mvvm.Module

import com.google.gson.GsonBuilder
import com.mj.mvvm.API.RetrofitConnection
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory


fun connnectionModule() = module {

    single {
        GsonBuilder().setLenient().create()
    }

    single {
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create(get()))
            .baseUrl("https://mjjang94.github.io/HostingRepository/")
            .build()
            .create(RetrofitConnection::class.java)
    }
}