package lyka.anime.animewallpaper.Repository

import lyka.anime.animewallpaper.DAO.FavouriteDao
import lyka.anime.animewallpaper.Entity.FavouriteEntity
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