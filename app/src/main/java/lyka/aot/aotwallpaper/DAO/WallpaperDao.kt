package lyka.aot.aotwallpaper.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import lyka.aot.aotwallpaper.Entity.Wallpaper


@Dao
interface WallpaperDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertWallpapers(wallpapers: MutableList<Wallpaper>)

    @Query("SELECT * FROM wallpapertb  WHERE cat_id = :cat_id")
    suspend fun getAllWallpapers(cat_id:Int): MutableList<Wallpaper>

    @Query("DELETE FROM wallpapertb")
    suspend fun deleteAllWallpapers()

}