package lyka.aot.animewallpapers.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import lyka.aot.animewallpapers.Repository.FavouriteRepository
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