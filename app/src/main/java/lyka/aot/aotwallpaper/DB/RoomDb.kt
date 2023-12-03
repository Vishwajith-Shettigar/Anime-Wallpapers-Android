package lyka.aot.aotwallpaper.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import lyka.aot.aotwallpaper.DAO.FavouriteDao
import lyka.aot.aotwallpaper.DAO.WallpaperDao
import lyka.aot.aotwallpaper.Entity.FavouriteEntity
import lyka.aot.aotwallpaper.Entity.Wallpaper


@Database(entities = [FavouriteEntity::class,Wallpaper::class],  exportSchema = false,version = 1)
abstract class RoomDb : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao

    abstract fun wallpaperDao(): WallpaperDao


}