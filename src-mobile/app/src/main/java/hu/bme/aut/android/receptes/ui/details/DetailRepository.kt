package hu.bme.aut.android.receptes.ui.details

import androidx.annotation.WorkerThread
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import com.skydoves.sandwich.suspendOnSuccess
import hu.bme.aut.android.receptes.model.Recipe
import hu.bme.aut.android.receptes.network.RecipeService
import hu.bme.aut.android.receptes.persistence.RecipeDao
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import retrofit2.Response
import javax.inject.Inject

class DetailRepository @Inject constructor(
    private val recipeService: RecipeService,
    private val recipeDao: RecipeDao
) {
    @WorkerThread
    fun getRecipeById(id: Long) = flow {
        val recipe = recipeDao.getRecipe(id)
        emit(recipe)
    }.flowOn(Dispatchers.IO)

    suspend fun insertRecipe(
        recipe: Recipe,
    ): Response<Recipe> {
        return recipeService.postRecipe(recipe)
    }

    fun insertOrUpdateRecipeToLocalDb(recipe: Recipe) {
        recipeDao.insertRecipe(recipe)
    }

    fun deleteRecipeFromLocalDb(recipe: Recipe) {
        recipeDao.deleteRecipe(recipe)
    }

    suspend fun updateRecipe(
        recipe: Recipe,
    ): Response<Recipe> {
        return recipeService.putRecipe(recipe)
    }

    suspend fun deleteRecipe(
        recipe: Recipe,
    ): Response<Recipe> {
        return recipeService.deleteRecipe(recipe.id)
    }

}