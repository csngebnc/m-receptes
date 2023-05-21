package hu.bme.aut.android.receptes.ui.list

import android.util.Log
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.receptes.model.Recipe
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.flatMapLatest
import javax.inject.Inject

@HiltViewModel
class RecipeListViewModel @Inject constructor(recipeListRepository: RecipeListRepository) :
    ViewModel() {

    private val usernameSharedFlow: MutableSharedFlow<String> = MutableSharedFlow(replay = 1)

    val recipesFlow = usernameSharedFlow.flatMapLatest {
        recipeListRepository.loadRecipes(it, {}, {}, {})
    }

    fun loadRecipes(username: String) = usernameSharedFlow.tryEmit(username)

}