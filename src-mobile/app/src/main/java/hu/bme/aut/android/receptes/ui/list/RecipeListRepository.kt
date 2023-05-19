package hu.bme.aut.android.receptes.ui.list

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
import javax.inject.Inject

class RecipeListRepository @Inject constructor(
    private val recipeService: RecipeService,
    private val recipeDao: RecipeDao
) {
    @WorkerThread
    fun loadRecipes(
        username: String,
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        val recipes: List<Recipe> = recipeDao.getRecipes(username)
        if (recipes.isEmpty()) {
            // request API network call asynchronously.
            recipeService.fetchRecipes(username)
                // handle the case when the API request gets a success response.
                .suspendOnSuccess {
                    if (data.isNotEmpty()) {
                        for (item in data) {
                            recipeDao.insertRecipe(item)
                        }
                    }
                    emit(data)
                }
                // handle the case when the API request is fails.
                // e.g. internal server error.
                .onFailure { onError(message()) }
        } else {
            emit(recipes)
        }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)


}