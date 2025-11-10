package com.example.vg_table.data.api

import com.example.vg_table.data.models.ProductResponse
import org.intellij.lang.annotations.Language
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import java.time.Month

interface ImpactC02service {

    //"fruitsetlegumes?month=1&category=1%2C2&language=fr"
    @GET("fruitsetlegumes")
    suspend fun getProducts(
        @Query("month") month: String,
        @Query("category") category: String = "1,2",
        @Query("language") language: String = "fr"
    ): ProductResponse
}