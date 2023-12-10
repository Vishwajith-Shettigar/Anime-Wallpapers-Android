package lyka.aot.animewallpaper.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import lyka.aot.animewallpaper.DAO.FavouriteDao
import lyka.aot.animewallpaper.DAO.WallpaperDao
import lyka.aot.animewallpaper.Entity.FavouriteEntity
import lyka.aot.animewallpaper.Entity.Wallpaper


@Database(entities = [FavouriteEntity::class,Wallpaper::class],  exportSchema = false,version = 1)
abstract class RoomDb : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao

    abstract fun wallpaperDao(): WallpaperDao


}