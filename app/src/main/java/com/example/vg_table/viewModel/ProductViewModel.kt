package com.example.vg_table.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vg_table.data.api.RetrofitInstance
import com.example.vg_table.data.models.Product
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class ProductViewModel: ViewModel() {
    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    init {
        val currentMonthId = SimpleDateFormat("MM", Locale.getDefault()).format(Date())
        fetchProducts(currentMonthId)
    }

    fun fetchProducts(month: String){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getProducts(month)
                _products.value = response.data
                Log.d("ProductViewModel", "Fetched ${response.data.size} produits")
                Log.d("API_RESPONSE", response.toString())
            }catch (e: Exception){
                Log.e("ProductViewModel", "Erreur API", e)
            }
        }
    }
}