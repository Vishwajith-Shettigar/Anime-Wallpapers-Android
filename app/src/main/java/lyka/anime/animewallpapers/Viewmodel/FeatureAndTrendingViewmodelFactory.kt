package lyka.anime.animewallpapers.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import lyka.anime.animewallpapers.Repository.FavouriteRepository

class FeatureAndTrendingViewmodelFactory @Inject constructor(
  private val firestore: FirebaseFirestore,
  private val favouriteRepository: FavouriteRepository
): ViewModelProvider.Factory  {

  override fun <T : ViewModel> create(modelClass: Class<T>): T {
    if (modelClass.isAssignableFrom(FeatureAndTrendingViewmodel::class.java)) {
      @Suppress("UNCHECKED_CAST")
      return FeatureAndTrendingViewmodel(firestore,favouriteRepository) as T
    }
    throw IllegalArgumentException("Unknown ViewModel class")
  }
}