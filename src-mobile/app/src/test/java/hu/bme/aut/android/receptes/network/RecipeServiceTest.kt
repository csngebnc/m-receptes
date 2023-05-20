package hu.bme.aut.android.receptes.network

import com.skydoves.sandwich.suspendOnSuccess
import kotlinx.coroutines.runBlocking
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4
import hu.bme.aut.android.receptes.model.Recipe
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.notNullValue

@RunWith(JUnit4::class)
class RecipeServiceTest : ApiMock() {
    @Test
    fun fetchRecipesResult_sentRequest_receivedExpected() {
        runBlocking {
            enqueueResponse("FetchRecipesResponse.json")

            var list = listOf<Recipe>()
            recipeService.fetchRecipes("string0").suspendOnSuccess { list = data }

            val request = mockWebServer.takeRequest()
            assertThat(list.size, equalTo(4))
            assertThat(request.path, equalTo("/all/string0"))
        }
    }

    @Test
    fun fetchRecipeResult_sentRequest_receivedExpected() {
        runBlocking {
            enqueueResponse("FetchRecipeResponse.json")

            var recipe: Recipe? = null
            recipeService.fetchRecipe(0).suspendOnSuccess { recipe = data }

            val request = mockWebServer.takeRequest()
            assertThat(recipe, `is`(notNullValue()))
            assertThat(recipe?.id, equalTo(0))
            assertThat(recipe?.name, equalTo("string0"))
            assertThat(recipe?.description, equalTo("string0"))
            assertThat(recipe?.ownerUsername, equalTo("string0"))
            assertThat(request.path, equalTo("/single/0"))
        }
    }
}