package com.example.vg_table.data.database

import kotlinx.coroutines.flow.Flow
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.vg_table.data.database.entities.RecipeEntity

@Dao
interface RecipeDAO {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(recipe: RecipeEntity)

    @Delete
    suspend fun delete(recipe: RecipeEntity)

    @Query("SELECT * from recipeBDD ORDER BY id ASC")
    fun getAllRecipes(): Flow<List<RecipeEntity>>
}