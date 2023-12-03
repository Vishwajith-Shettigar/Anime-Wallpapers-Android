package com.example.aotwallpaper.Repository

import android.util.Log
import com.example.aotwallpaper.DAO.FavouriteDao
import com.example.aotwallpaper.DAO.WallpaperDao
import com.example.aotwallpaper.Entity.FavouriteEntity
import com.example.aotwallpaper.Entity.Wallpaper
import javax.inject.Inject

class WallpaperRepository  @Inject constructor(private val wallpaperDao: WallpaperDao) {

    suspend fun insert(wallpaperlist: MutableList<Wallpaper>) {
        wallpaperDao.insertWallpapers(wallpaperlist)
    }

    suspend fun getAllWallpapers(cat_id:Int):MutableList<Wallpaper>{
      return  wallpaperDao.getAllWallpapers(cat_id)
    }

    suspend fun deleteAll(){
        wallpaperDao.deleteAllWallpapers()
    }
}