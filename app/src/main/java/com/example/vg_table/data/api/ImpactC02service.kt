package com.example.vg_table.data.api

import com.example.vg_table.data.models.ProductResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ImpactC02service {

    @GET("fruitsetlegumes")
    suspend fun getProducts(
        @Query("month") month: String,
        @Query("category") category: String = "1,2",
        @Query("language") language: String = "fr"
    ): ProductResponse
}