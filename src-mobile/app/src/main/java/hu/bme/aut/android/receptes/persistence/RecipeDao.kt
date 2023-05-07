package hu.bme.aut.android.receptes.persistence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import hu.bme.aut.android.receptes.model.Recipe

@Dao
interface RecipeDao {
    @Query("SELECT * FROM recipes")
    fun getRecipes(): List<Recipe>
    @Query("SELECT * FROM recipes WHERE id = :recipeId LIMIT 1")
    fun getRecipe(recipeId: Int): Recipe
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertRecipe(grade: Recipe)
    @Delete
    fun deleteRecipe(grade: Recipe)
}