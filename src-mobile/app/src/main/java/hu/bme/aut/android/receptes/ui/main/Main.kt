package hu.bme.aut.android.receptes.ui.main

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import hu.bme.aut.android.receptes.model.Recipe
import hu.bme.aut.android.receptes.ui.theme.ReceptesTheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.unit.dp

@Composable
fun RecipesMainScreen(viewModel: MainViewModel) {
    val items: List<Recipe> by viewModel.recipeList.collectAsState(initial = listOf())
    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Text(text = "Receptek")
        items.forEach { poster ->
            key(poster.id) {

                Text(
                    modifier = Modifier.padding(top = 20.dp),
                    text = poster.name
                )
            }
        }
    }
}