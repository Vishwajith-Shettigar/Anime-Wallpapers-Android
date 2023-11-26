package com.example.aotwallpaper.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aotwallpaper.DAO.FavouriteDao
import com.example.aotwallpaper.DAO.WallpaperDao
import com.example.aotwallpaper.Entity.FavouriteEntity
import com.example.aotwallpaper.Entity.Wallpaper


@Database(entities = [FavouriteEntity::class,Wallpaper::class],  exportSchema = false,version = 1)
abstract class RoomDb : RoomDatabase() {

    abstract fun favouriteDao(): FavouriteDao

    abstract fun wallpaperDao(): WallpaperDao


}