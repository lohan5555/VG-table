package com.example.vg_table.data.database

import androidx.lifecycle.ViewModelProvider.AndroidViewModelFactory.Companion.APPLICATION_KEY
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.vg_table.VGApplication
import com.example.vg_table.viewModel.RecipeViewModel

object AppViewModelProvider {
    val Factory = viewModelFactory {
        initializer {
            val app = this[APPLICATION_KEY] as VGApplication
            RecipeViewModel(app.container.itemsRepository)
        }
    }
}
