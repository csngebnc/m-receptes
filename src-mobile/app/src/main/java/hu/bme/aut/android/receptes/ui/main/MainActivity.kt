package hu.bme.aut.android.receptes.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.receptes.ui.theme.ReceptesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            ReceptesTheme {
                RecipesMainScreen(
                    viewModel = hiltViewModel()
                )
            }
        }
    }
}