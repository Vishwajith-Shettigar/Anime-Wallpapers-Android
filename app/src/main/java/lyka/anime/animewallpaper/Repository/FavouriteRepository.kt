package lyka.aot.animewallpaper.Repository

import lyka.aot.animewallpaper.DAO.FavouriteDao
import lyka.aot.animewallpaper.Entity.FavouriteEntity
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