package com.example.aotwallpaper.Repository

import com.example.aotwallpaper.DAO.FavouriteDao
import com.example.aotwallpaper.Entity.FavouriteEntity
import javax.inject.Inject

class FavouriteRepository @Inject constructor(private val favouriteDao: FavouriteDao) {

    suspend fun insert(favouriteEntity: FavouriteEntity) {
        favouriteDao.insert(favouriteEntity)
    }

    suspend fun getAllNotes(): List<FavouriteEntity> {
        return favouriteDao.getAllFavourites()
    }
}