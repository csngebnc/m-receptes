package hu.bme.aut.android.receptes.ui.main

import androidx.annotation.WorkerThread
import hu.bme.aut.android.receptes.model.Recipe
import hu.bme.aut.android.receptes.network.RecipeService
import hu.bme.aut.android.receptes.persistence.RecipeDao
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import com.skydoves.sandwich.message
import com.skydoves.sandwich.onFailure
import javax.inject.Inject

import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.Dispatchers

class MainRepository @Inject constructor(
    private val recipeService: RecipeService,
    private val recipeDao: RecipeDao
) {}