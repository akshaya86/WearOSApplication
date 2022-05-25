package com.example.data.network

import com.example.data.network.model.HeartInfoResponse
import okhttp3.RequestBody
import retrofit2.Response
import retrofit2.http.POST

interface HeartRateApi {

    @POST
    suspend fun uploadHeartRate(): Response<HeartInfoResponse>

}