package hu.bme.aut.android.receptes.ui.details

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import hu.bme.aut.android.receptes.model.Recipe
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext

@SuppressLint("StateFlowValueCalledInComposition")
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun RecipeDetails(
    username: String,
    recipeId: Long?,
    onBack: () -> Unit,
    viewModel: DetailViewModel
) {
    var recipe by remember {
        mutableStateOf(
            Recipe(
                id = -1,
                ownerUsername = username,
                description = "",
                name = ""
            )
        )
    }
    var isEditing by remember { mutableStateOf(false) }
    var isPost by remember { mutableStateOf(false) }
    var editedName by remember { mutableStateOf(recipe.name) }
    var editedDescription by remember { mutableStateOf(recipe.description) }
    val data: ResponseData by viewModel.viewState.collectAsState()

    LaunchedEffect(key1 = recipeId) {
        if (recipeId != null) {
            viewModel.loadRecipeById(recipeId)
            viewModel.recipeDetails.collect {
                recipe = it
                editedName = it.name
                editedDescription = it.description
            }
        } else {
            isPost = true
            isEditing = true
        }
    }

    fun delete() {
        if (recipeId == null) {
            recipe.id = data.id
        }

        viewModel.delete(recipe, onBack)
    }

    fun save() {
        if (isPost) {
            viewModel.insert(
                Recipe(
                    name = editedName,
                    description = editedDescription,
                    ownerUsername = username,
                    id = 0
                )
            )

            isPost = false
            isEditing = false
        } else {
            if (recipeId == null) {
                recipe.id = data.id
            }

            viewModel.update(
                Recipe(
                    name = editedName,
                    description = editedDescription,
                    ownerUsername = username,
                    id = recipe.id
                )
            )
        }
    }




    Column(
        verticalArrangement = Arrangement.SpaceBetween,
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
    ) {
        Row {
            Column {
                Text(
                    text = "Recept neve",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                if (isEditing) {
                    TextField(
                        value = editedName,
                        onValueChange = { editedName = it },
                        modifier = Modifier.padding(bottom = 16.dp),
                        singleLine = true
                    )
                } else {
                    Text(
                        text = recipe.name,
                        fontSize = 20.sp,
                        modifier = Modifier.padding(bottom = 16.dp)
                    )
                }

                Text(
                    text = "Recept leírása",
                    fontSize = 30.sp,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                if (isEditing) {
                    TextField(
                        value = editedDescription,
                        onValueChange = { editedDescription = it },
                        modifier = Modifier.padding(bottom = 8.dp),
                    )
                } else {
                    Text(
                        text = recipe.description,
                        fontSize = 20.sp,
                    )
                }
            }
        }

        Row(
            horizontalArrangement = Arrangement.End
        ) {
            if (!isEditing) {
                Button(
                    onClick = { onBack() },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = "Vissza")
                }

                Button(
                    onClick = { isEditing = true },
                    modifier = Modifier.padding(end = 8.dp)
                ) {
                    Text(text = "Szerkeszt")
                }

                Button(
                    onClick = {
                        delete()
                    }
                ) {
                    Text(text = "Töröl")
                }

            } else {
                Column(
                ) {
                    Button(
                        modifier = Modifier.align(Alignment.CenterHorizontally),
                        onClick = {
                            runBlocking {
                                withContext(Dispatchers.IO) {
                                    save()
                                }
                            }
                            isEditing = false
                            recipe.name = editedName
                            recipe.description = editedDescription
                        }
                    ) {
                        Text(text = "Mentés")
                    }
                }
            }
        }
    }
}