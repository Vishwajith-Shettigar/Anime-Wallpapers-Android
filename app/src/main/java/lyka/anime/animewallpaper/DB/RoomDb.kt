package lyka.anime.animewallpaper.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import lyka.anime.animewallpaper.DAO.FavouriteDao
import lyka.anime.animewallpaper.DAO.WallpaperDao
import lyka.anime.animewallpaper.Entity.FavouriteEntity
import lyka.anime.animewallpaper.Entity.Wallpaper


@Database(entities = [FavouriteEntity::class,Wallpaper::class],  exportSchema = false,version = 1)
abstract class RoomDb : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao

    abstract fun wallpaperDao(): WallpaperDao


}