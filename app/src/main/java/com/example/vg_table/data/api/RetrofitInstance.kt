package com.example.vg_table.data.api

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitInstance {
    val apiImpactC02: ImpactC02service by lazy {
        Retrofit.Builder()
            .baseUrl("https://impactco2.fr/api/v1/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ImpactC02service::class.java)
    }

    val apiSpoonacular: SpoonacularService by lazy{
        Retrofit.Builder()
            .baseUrl("https://api.spoonacular.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(SpoonacularService::class.java)
    }
}