package com.example.vg_table.viewModel

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.vg_table.data.api.RetrofitInstance
import com.example.vg_table.data.models.Product
import kotlinx.coroutines.launch

class ProductViewModel: ViewModel() {
    private val _products = mutableStateOf<List<Product>>(emptyList())
    val products: State<List<Product>> = _products

    init {
        fetchProducts()
    }

    private fun fetchProducts(){
        viewModelScope.launch {
            try {
                val response = RetrofitInstance.api.getProducts()
                _products.value = response.data
                Log.d("ProductViewModel", "Fetched ${response.data.size} produits")
                Log.d("API_RESPONSE", response.toString())
            }catch (e: Exception){
                Log.e("ProductViewModel", "Erreur API", e)
            }
        }
    }
}