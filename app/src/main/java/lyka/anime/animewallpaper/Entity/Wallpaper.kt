package lyka.aot.animewallpaper.Entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "wallpapertb")
data class Wallpaper(
    @PrimaryKey(autoGenerate = false)
    val id: String,
    val cat_id:Int,
    val imageUrl: String
)