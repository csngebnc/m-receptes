package hu.bme.aut.android.receptes.persistence

import hu.bme.aut.android.receptes.MainCoroutinesRule
import hu.bme.aut.android.receptes.model.Recipe
import kotlinx.coroutines.test.runTest
import org.hamcrest.MatcherAssert.assertThat
import org.hamcrest.core.Is.`is`
import org.hamcrest.core.IsEqual.equalTo
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(sdk = [24])
class RecipeDaoTest : LocalDatabase() {

    private lateinit var recipeDao: RecipeDao

    @get:Rule
    val coroutinesRule = MainCoroutinesRule()

    @Before
    fun init() {
        recipeDao = db.recipeDao()
    }

    @Test
    fun insertAndGetRecipeTest() = runTest {
        val mockRecipe = Recipe.mock()
        recipeDao.insertRecipe(mockRecipe)

        val loadFromDB = recipeDao.getRecipes()
        assertThat(loadFromDB.size, equalTo(1))
        assertThat(loadFromDB[0].toString(), `is`(mockRecipe.toString()))
    }
}