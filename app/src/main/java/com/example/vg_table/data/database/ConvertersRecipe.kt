package com.example.vg_table.data.database

import androidx.room.TypeConverter
import com.example.vg_table.data.models.searchRecipe.AnalyzedInstruction
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

class ConvertersRecipe {
    private val gson = Gson()

    //convertit une List<AnalyzedInstruction> en un json
    @TypeConverter
    fun fromAnalyzedInstructions(list: List<AnalyzedInstruction>): String {
        return gson.toJson(list)
    }

    //convertit un json en une List<AnalyzedInstruction>
    @TypeConverter
    fun toAnalyzedInstructions(json: String): List<AnalyzedInstruction> {
        val type = object : TypeToken<List<AnalyzedInstruction>>() {}.type
        return gson.fromJson(json, type)
    }


}