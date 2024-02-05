package com.example.data.remote

import com.example.domain.models.ListProduct
import retrofit2.http.GET

interface ApiService {
    @GET("v3/97e721a7-0a66-4cae-b445-83cc0bcf9010")
    suspend fun getListOfProduct(): ListProduct
}