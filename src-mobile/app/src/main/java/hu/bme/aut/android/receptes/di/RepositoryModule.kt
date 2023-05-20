package hu.bme.aut.android.receptes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import hu.bme.aut.android.receptes.network.RecipeService
import hu.bme.aut.android.receptes.persistence.RecipeDao
import hu.bme.aut.android.receptes.ui.details.DetailRepository
import hu.bme.aut.android.receptes.ui.list.RecipeListRepository

@Module
@InstallIn(ViewModelComponent::class)
object RepositoryModule {
    @Provides
    @ViewModelScoped
    fun provideDetailRepository(
        recipeService: RecipeService,
        recipeDao: RecipeDao
    ): DetailRepository {
        return DetailRepository(recipeService, recipeDao)
    }
    @Provides
    @ViewModelScoped
    fun provideRecipeListRepository(
        recipeService: RecipeService,
        recipeDao: RecipeDao
    ): RecipeListRepository {
        return RecipeListRepository(recipeService, recipeDao)
    }
}