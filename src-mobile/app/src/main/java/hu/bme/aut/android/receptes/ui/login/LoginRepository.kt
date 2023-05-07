package hu.bme.aut.android.receptes.ui.login

import hu.bme.aut.android.receptes.network.RecipeService
import hu.bme.aut.android.receptes.persistence.RecipeDao
import javax.inject.Inject

class LoginRepository @Inject constructor(
    private val recipeService: RecipeService,
    private val recipeDao: RecipeDao
) {
    // TODO: not really necessary
}