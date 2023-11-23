package com.example.aotwallpaper.DI

import android.app.Application
import android.content.Context
import android.util.Log
import androidx.room.Room
import com.example.aotwallpaper.DAO.FavouriteDao
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
        Log.e("#",context.toString())
        return Room.databaseBuilder(
            context,
            RoomDb::class.java,
            "roomdb"
        )
            .allowMainThreadQueries()
            .build()
    }

    @Provides
    @Singleton
    fun provideNoteDao(noteDatabase: RoomDb): FavouriteDao {
        return noteDatabase.favouriteDao()
    }
}
