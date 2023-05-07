package hu.bme.aut.android.receptes.ui.editor

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

class EditorRepository @Inject constructor(
    private val recipeService: RecipeService,
    private val recipeDao: RecipeDao
) {
    @WorkerThread
    fun insertRecipe(
        recipe: Recipe,
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
            recipe.ownerUsername = "csngebnc" // TODO REPLACE
            // request API network call asynchronously.
            recipeService.postRecipe(recipe)
                // handle the case when the API request gets a success response.
                .suspendOnSuccess {
                    recipe.id = data.id
                    recipeDao.insertRecipe(recipe)
                    emit(data)
                }
                // handle the case when the API request is fails.
                // e.g. internal server error.
                .onFailure { onError(message()) }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)

    @WorkerThread
    fun updateRecipe(
        recipe: Recipe,
        onStart: () -> Unit,
        onCompletion: () -> Unit,
        onError: (String) -> Unit
    ) = flow {
        recipe.ownerUsername = "csngebnc" // TODO REPLACE
        // request API network call asynchronously.
        recipeService.putRecipe(recipe)
            // handle the case when the API request gets a success response.
            .suspendOnSuccess {
                recipeDao.insertRecipe(recipe)
                emit(data)
            }
            // handle the case when the API request is fails.
            // e.g. internal server error.
            .onFailure { onError(message()) }
    }.onStart { onStart() }.onCompletion { onCompletion() }.flowOn(Dispatchers.IO)
}