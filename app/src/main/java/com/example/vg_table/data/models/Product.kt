package com.example.vg_table.data.models

data class Product (
    val name: String = "",
    val slug: String = "",
    var months: ArrayList<Int> = arrayListOf(),
    var ecv: Double? = null,
    var category: String? = null
)

data class ProductResponse(
    val data: List<Product>
)