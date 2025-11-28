package com.example.vg_table.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.vg_table.data.database.entities.RecipeEntity

@Database(entities = [RecipeEntity::class], version = 2, exportSchema = false)
abstract class RecipeDataBase: RoomDatabase() {

    abstract fun recipeDao() : RecipeDAO

    companion object{
        @Volatile
        private var Instance: RecipeDataBase? = null

        fun getDatabase(context: Context): RecipeDataBase {
            return Instance ?: synchronized(this) {
                Room.databaseBuilder(context, RecipeDataBase::class.java, "recipeBDD")
                    .fallbackToDestructiveMigration(false)
                    .build()
                    .also { Instance = it }
            }
        }
    }
}