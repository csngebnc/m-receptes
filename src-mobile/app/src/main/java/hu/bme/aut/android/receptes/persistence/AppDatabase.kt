package hu.bme.aut.android.receptes.persistence

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import hu.bme.aut.android.receptes.model.Recipe

@Database(entities = [Recipe::class], version = 1, exportSchema = true  )
abstract class AppDatabase : RoomDatabase() {
    abstract fun recipeDao(): RecipeDao
}