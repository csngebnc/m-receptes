package hu.bme.aut.android.receptes.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
@Immutable
data class Recipe(
    @PrimaryKey(autoGenerate = true) var id: Int,
    var name: String,
    var description: String,
    var ownerUsername: String,
) {
    companion object {
        fun mock() = Recipe(
            id = 1,
            name = "Baracklekváros fánk",
            description = "Nagyon fincsa, én nem tudom a receptet, de anya tudja. Bibibi",
            ownerUsername = "csngebnc"
        )
    }
}