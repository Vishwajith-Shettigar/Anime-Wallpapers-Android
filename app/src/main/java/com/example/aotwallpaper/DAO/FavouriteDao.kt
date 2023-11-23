package com.example.aotwallpaper.DAO

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.aotwallpaper.Entity.FavouriteEntity

@Dao
interface FavouriteDao {
    @Insert
    suspend fun insert(note: FavouriteEntity)

    @Query("SELECT * FROM favouritetb")
    suspend fun getAllFavourites(): List<FavouriteEntity>
}