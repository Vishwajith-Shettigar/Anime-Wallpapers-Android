package lyka.aot.animewallpapers.Repository

import lyka.aot.animewallpapers.DAO.WallpaperDao
import lyka.aot.animewallpapers.Entity.Wallpaper
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