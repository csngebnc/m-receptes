package hu.bme.aut.android.receptes.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped
import hu.bme.aut.android.receptes.network.RecipeService
import hu.bme.aut.android.receptes.persistence.RecipeDao
import hu.bme.aut.android.receptes.ui.details.DetailRepository
import hu.bme.aut.android.receptes.ui.editor.EditorRepository
import hu.bme.aut.android.receptes.ui.login.LoginRepository
import hu.bme.aut.android.receptes.ui.main.MainRepository

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
    fun provideEditorRepository(
        recipeService: RecipeService,
        recipeDao: RecipeDao
    ): EditorRepository {
        return EditorRepository(recipeService, recipeDao)
    }
    @Provides
    @ViewModelScoped
    fun provideLoginRepository(
        recipeService: RecipeService,
        recipeDao: RecipeDao
    ): LoginRepository {
        return LoginRepository(recipeService, recipeDao)
    }
    @Provides
    @ViewModelScoped
    fun provideMainRepository(
        recipeService: RecipeService,
        recipeDao: RecipeDao
    ): MainRepository {
        return MainRepository(recipeService, recipeDao)
    }
}