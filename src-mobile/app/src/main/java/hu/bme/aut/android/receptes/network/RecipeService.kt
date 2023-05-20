package hu.bme.aut.android.receptes.network

import com.skydoves.sandwich.ApiResponse
import hu.bme.aut.android.receptes.model.Recipe
import kotlinx.coroutines.flow.Flow
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.DELETE
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RecipeService {
    @GET("all/{userName}")
    suspend fun fetchRecipes(@Path("userName") userName: String): ApiResponse<List<Recipe>>

    @GET("single/{recipeId}")
    suspend fun fetchRecipe(@Path("recipeId") recipeId: Int): ApiResponse<Recipe>

    @POST("post")
    suspend fun postRecipe(@Body recipe: Recipe): Response<Recipe>

    @PUT("put")
    suspend fun putRecipe(@Body recipe: Recipe): Response<Recipe>

    @DELETE("delete/{recipeId}")
    suspend fun deleteRecipe(@Path("recipeId") recipeId: Long): Response<Recipe>

}