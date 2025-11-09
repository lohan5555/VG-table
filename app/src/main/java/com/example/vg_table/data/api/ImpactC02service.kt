package com.example.vg_table.data.api

import com.example.vg_table.data.models.ProductResponse
import retrofit2.http.GET

interface ImpactC02service {
    @GET("fruitsetlegumes?month=6&category=1%2C2&language=fr")
    suspend fun getProducts(): ProductResponse
}