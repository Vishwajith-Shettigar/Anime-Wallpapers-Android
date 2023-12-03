package lyka.aot.aotwallpaper.DI

import android.app.Application
import androidx.room.Room
import lyka.aot.aotwallpaper.DAO.FavouriteDao
import lyka.aot.aotwallpaper.DAO.WallpaperDao
import lyka.aot.aotwallpaper.DB.RoomDb
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
