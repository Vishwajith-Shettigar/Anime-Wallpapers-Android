package lyka.anime.animewallpapers.DB

import androidx.room.Database
import androidx.room.RoomDatabase
import lyka.anime.animewallpapers.DAO.FavouriteDao
import lyka.anime.animewallpapers.DAO.WallpaperDao
import lyka.anime.animewallpapers.Entity.FavouriteEntity
import lyka.anime.animewallpapers.Entity.Wallpaper


@Database(entities = [FavouriteEntity::class,Wallpaper::class],  exportSchema = false,version = 1)
abstract class RoomDb : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao

    abstract fun wallpaperDao(): WallpaperDao


}