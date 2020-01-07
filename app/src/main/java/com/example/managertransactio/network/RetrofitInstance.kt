package com.example.managertransactio.network

import com.google.gson.JsonElement
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

class RetrofitInstance {
    fun getRetrofitTempInstance(): Retrofit {
        val requestInterface = Retrofit.Builder()
            .baseUrl("https://openexchangerates.org/api/")
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        return requestInterface
        }

    }


