package hu.bme.aut.android.receptes.ui.main

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.hilt.navigation.compose.hiltViewModel
import com.google.firebase.analytics.FirebaseAnalytics
import com.google.firebase.analytics.ktx.analytics
import com.google.firebase.ktx.Firebase
import dagger.hilt.android.AndroidEntryPoint
import hu.bme.aut.android.receptes.ui.theme.ReceptesTheme

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    private lateinit var analytics: FirebaseAnalytics
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        analytics = Firebase.analytics
        setContent {
            ReceptesTheme {
                RecipesMainScreen(analytics)
            }
        }
    }
}