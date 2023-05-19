package hu.bme.aut.android.receptes.model

import androidx.compose.runtime.Immutable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "recipes")
@Immutable
data class Recipe(
    @SerializedName("id")@PrimaryKey(autoGenerate = true) var id: Long,
    @SerializedName("name")var name: String,
    @SerializedName("description")var description: String,
    @SerializedName("ownerUsername")var ownerUsername: String,
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