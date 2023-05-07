package hu.bme.aut.android.receptes.ui.main

import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.receptes.model.Recipe
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(mainRepository: MainRepository) : ViewModel() {

    val recipeList: Flow<List<Recipe>> =
        mainRepository.loadRecipes(
            onStart = {  },
            onCompletion = {  },
            onError = {  }
        )

}