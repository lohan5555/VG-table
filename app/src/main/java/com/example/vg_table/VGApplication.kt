package com.example.vg_table

import android.app.Application
import com.example.vg_table.data.database.AppContainer
import com.example.vg_table.data.database.AppDataContainer

class VGApplication : Application() {
    lateinit var container: AppContainer

    override fun onCreate() {
        super.onCreate()
        container = AppDataContainer(this)
    }
}
