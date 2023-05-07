package hu.bme.aut.android.receptes.network

import com.skydoves.sandwich.ApiResponse
import hu.bme.aut.android.receptes.model.Recipe
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface RecipeService {
    @GET("all/{userName}")
    suspend fun fetchRecipes(@Path("userName") userName: String): ApiResponse<List<Recipe>>
    @GET("single/{recipeId}")
    suspend fun fetchRecipe(@Path("recipeId") recipeId: Int): ApiResponse<Recipe>
    @POST
    fun postRecipe(@Body recipe: Recipe): ApiResponse<Recipe>
    @PUT
    fun putRecipe(@Body recipe: Recipe): ApiResponse<Recipe>
    @GET("delete/{recipeId}")
    suspend fun deleteRecipe(@Path("recipeId") recipeId: String)

}