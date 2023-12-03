package lyka.aot.aotwallpaper.Repository

import lyka.aot.aotwallpaper.DAO.FavouriteDao
import lyka.aot.aotwallpaper.Entity.FavouriteEntity
import javax.inject.Inject

class FavouriteRepository @Inject constructor(private val favouriteDao: FavouriteDao) {

    suspend fun insert(favouriteEntity: FavouriteEntity) {
        favouriteDao.insert(favouriteEntity)
    }

    suspend fun getAllFavourites(): MutableList<FavouriteEntity> {
        return favouriteDao.getAllFavourites()
    }

    suspend fun deleteFavourite(id: String){
        return favouriteDao.deleteById(id)

    }

    suspend fun deleteAll(){
        favouriteDao.deleteAllData()
    }
}