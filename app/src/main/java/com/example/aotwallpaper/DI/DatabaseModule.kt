package com.example.aotwallpaper.DI

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.aotwallpaper.DAO.FavouriteDao
import com.example.aotwallpaper.DAO.WallpaperDao
import com.example.aotwallpaper.DB.RoomDb
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

// DatabaseModule.kt
@Module
class DatabaseModule {

    @Provides
    @Singleton
    fun provideNoteDatabase(context: Application): RoomDb {
        return Room.databaseBuilder(
            context,
            RoomDb::class.java,
            "roomdb"
        ).build()
    }

    @Provides
    @Singleton
    fun provideFavouriteDao(roomdb: RoomDb): FavouriteDao {
        return roomdb.favouriteDao()
    }

    @Provides
    @Singleton
    fun provideWallpaperDao(roomdb: RoomDb): WallpaperDao {
        return roomdb.wallpaperDao()
    }
}
