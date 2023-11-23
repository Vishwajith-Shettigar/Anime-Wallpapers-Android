package com.example.aotwallpaper.DB

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import com.example.aotwallpaper.DAO.FavouriteDao
import com.example.aotwallpaper.Entity.FavouriteEntity


@Database(entities = [FavouriteEntity::class],  exportSchema = false,version = 1)
abstract class RoomDb : RoomDatabase() {



    abstract fun favouriteDao(): FavouriteDao


}