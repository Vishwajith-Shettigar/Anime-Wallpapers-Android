package lyka.aot.aotwallpaper.Repository

import lyka.aot.aotwallpaper.DAO.WallpaperDao
import lyka.aot.aotwallpaper.Entity.Wallpaper
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