package com.example.managertransactio.network

import com.google.gson.JsonElement
import io.reactivex.Observable
import retrofit2.Response
import retrofit2.http.GET

interface GetDataService {
    @GET("latest.json?app_id=f088f13adf1841dd86be6c168e0b95d4")
    fun ChangPrice(): Observable<Response<JsonElement>>
}