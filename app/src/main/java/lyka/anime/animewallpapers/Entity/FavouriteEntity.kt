package lyka.anime.animewallpapers.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "favouritetb")
data class FavouriteEntity(

    @PrimaryKey(autoGenerate = false)
    val id: String ,
    val imageurl:String
)
