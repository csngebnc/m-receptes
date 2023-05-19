package hu.bme.aut.android.receptes.ui.details

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import hu.bme.aut.android.receptes.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import retrofit2.Response
import javax.inject.Inject

@HiltViewModel
class DetailViewModel @Inject constructor(
    private val detailRepository: DetailRepository
) : ViewModel() {
    private val _viewState = MutableStateFlow(ResponseData())
    val viewState = _viewState.asStateFlow()

    private val recipeIdSharedFlow: MutableSharedFlow<Long> = MutableSharedFlow(replay = 1)

    val recipeDetails = recipeIdSharedFlow.flatMapLatest {
        detailRepository.getRecipeById(it)
    }


    fun loadRecipeById(id: Long) = recipeIdSharedFlow.tryEmit(id)

    fun insert(recipe: Recipe) {
        viewModelScope.launch {
            var response = detailRepository.insertRecipe(recipe)
            runBlocking(context = Dispatchers.IO) {
                detailRepository.insertOrUpdateRecipeToLocalDb(response.body()!!)
            }
            Log.d("INSERT", response.body()!!.id.toString())
            val newUIState = _viewState.value.copy(id = response.body()!!.id)
            _viewState.value = newUIState // emit the new UI state
        }
    }

    fun update(recipe: Recipe) {
        viewModelScope.launch {
            var response = detailRepository.updateRecipe(recipe)
            runBlocking(context = Dispatchers.IO) {
                detailRepository.insertOrUpdateRecipeToLocalDb(response.body()!!)
            }

            val newUIState = _viewState.value.copy(id = response.body()!!.id)
            _viewState.value = newUIState // emit the new UI state
        }
    }

    fun delete(recipe: Recipe, onBack: () -> Unit) {
        viewModelScope.launch {
            var response = detailRepository.deleteRecipe(recipe)
            Log.d("RES", response.body().toString())
            runBlocking(context = Dispatchers.IO) {
                detailRepository.deleteRecipeFromLocalDb(recipe)
            }
            onBack()
        }
    }
}