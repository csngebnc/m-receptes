package hu.bme.aut.android.receptes.ui.list

import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.Divider
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.key
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import hu.bme.aut.android.receptes.model.Recipe
import androidx.compose.material3.Text
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.text.font.FontWeight
import androidx.lifecycle.viewmodel.compose.viewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.analytics.ktx.logEvent
import com.google.firebase.ktx.Firebase


@Composable
fun RecipeList(
    analytics: FirebaseAnalytics,
    username: String,
    selectRecipe: (Long) -> Unit,
    addRecipe: () -> Unit,
    viewModel: RecipeListViewModel
) {

    LaunchedEffect(key1 = username) {
        viewModel.loadRecipes(username)
    }

    val items: List<Recipe> by viewModel.recipesFlow.collectAsState(initial = listOf())

    Box(modifier = Modifier.fillMaxSize()) {
        Column(modifier = Modifier.fillMaxSize()
            .verticalScroll(rememberScrollState())) {

            Text(
                text = "Receptjeim",
                style = MaterialTheme.typography.headlineMedium,
                modifier = Modifier
                    .padding(16.dp),
            )

            if (items.isNotEmpty()) {
                Separator()

                items.forEach { item ->
                    key(item.id) {
                        Row(modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                analytics.logEvent(FirebaseAnalytics.Event.SELECT_CONTENT) {
                                    param(FirebaseAnalytics.Param.ITEM_ID, item.id);
                                    param(FirebaseAnalytics.Param.ITEM_NAME, item.name);
                                }
                                selectRecipe(item.id)
                            }) {
                            ListItem(item)
                        }
                        Separator()
                    }
                }
            } else {
                Text(
                    text = "Még nincs recepted.",
                    modifier = Modifier.padding(16.dp)
                )
            }
        }

        FloatingActionButton(
            onClick = { addRecipe() },
            modifier = Modifier
                .align(Alignment.BottomEnd)
                .padding(16.dp)
        ) {
            Icon(
                imageVector = Icons.Default.Add,
                contentDescription = "Add"
            )
        }
    }
}

@Composable
fun ListItem(recipe: Recipe) {
    Column(modifier = Modifier.padding(vertical = 16.dp, horizontal = 16.dp)) {
        Text(
            text = recipe.name,
            style = MaterialTheme.typography.labelLarge.copy(fontWeight = FontWeight.Bold)
        )
        Text(
            text = recipe.description.take(17) + "...",
            style = MaterialTheme.typography.bodyMedium
        )
    }
}

@Composable
fun Separator() {
    Divider(
        modifier = Modifier.padding(horizontal = 16.dp),
        thickness = 1.dp,
        color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.12f)
    )
}