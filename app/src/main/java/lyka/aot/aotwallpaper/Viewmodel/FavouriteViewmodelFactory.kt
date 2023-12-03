package lyka.aot.aotwallpaper.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lyka.aot.aotwallpaper.Repository.FavouriteRepository
import javax.inject.Inject

class FavouriteViewmodelFactory @Inject constructor(
    private val favouriteRepository: FavouriteRepository

):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(FavouriteViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return FavouriteViewmodel(favouriteRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}