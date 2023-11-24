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
    suspend fun getAllFavourites(): MutableList<FavouriteEntity>

    @Query("DELETE FROM favouritetb")
   suspend fun deleteAllData()

    @Query("DELETE FROM favouritetb WHERE id = :id")
    suspend fun deleteById(id: String)
}