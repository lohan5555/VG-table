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

    //Avec Flow comme type renvoyé,
    // - vous recevez une notification chaque fois que les données de la base de données changent
    // - Room exécute également la requête sur le thread d'arrière-plan. Vous n'avez pas besoin de la définir explicitement comme une fonction suspend et de l'appeler dans le cadre d'une coroutine.
    @Query("SELECT * from recipeBDD ORDER BY id ASC")
    fun getAllRecipes(): Flow<List<RecipeEntity>>
}