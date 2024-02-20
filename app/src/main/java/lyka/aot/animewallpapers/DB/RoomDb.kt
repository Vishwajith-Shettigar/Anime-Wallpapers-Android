package lyka.aot.animewallpapers.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import lyka.aot.animewallpapers.DAO.FavouriteDao
import lyka.aot.animewallpapers.DAO.WallpaperDao
import lyka.aot.animewallpapers.Entity.FavouriteEntity
import lyka.aot.animewallpapers.Entity.Wallpaper


@Database(entities = [FavouriteEntity::class,Wallpaper::class],  exportSchema = false,version = 1)
abstract class RoomDb : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao

    abstract fun wallpaperDao(): WallpaperDao


}