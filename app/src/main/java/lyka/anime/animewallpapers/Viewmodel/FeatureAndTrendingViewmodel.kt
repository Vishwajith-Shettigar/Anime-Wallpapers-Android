package lyka.anime.animewallpapers.Viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject
import kotlin.random.Random
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import lyka.anime.animewallpapers.Data.Category
import lyka.anime.animewallpapers.Entity.FavouriteEntity
import lyka.anime.animewallpapers.Entity.Wallpaper
import lyka.anime.animewallpapers.Repository.FavouriteRepository

class FeatureAndTrendingViewmodel(
  private val firestore: FirebaseFirestore,
  private val favouriteRepository: FavouriteRepository,
) : ViewModel() {

  private var favouriteList = mutableListOf<FavouriteEntity>()
  private val _threeWallpapers = MutableStateFlow<MutableList<Wallpaper>>(mutableListOf())
  val threeWallpapers: StateFlow<MutableList<Wallpaper>> get() = _threeWallpapers

  private val _trendingWallpapers = MutableStateFlow<MutableList<Wallpaper>>(mutableListOf())
  val trendingWallpapers: StateFlow<MutableList<Wallpaper>> get() = _trendingWallpapers


  private val _category = MutableStateFlow<Category>(Category(-1, "", ""))
  val category: StateFlow<Category> get() = _category

  private suspend fun loadFavourites() {
    favouriteList = favouriteRepository.getAllFavourites()
  }

  init {
    viewModelScope.launch {
      getCategory()
      getThreeWallpapers()
      loadFavourites()
      getTrendingWallpapers()
    }


  }

   fun getTrendingWallpapers() {
    val datalist: MutableList<Wallpaper> = mutableListOf()
    viewModelScope.launch {
      try {
        firestore.collection("AOT").document("images").collection("trending").get()
          .addOnCompleteListener {

            if (it.isSuccessful) {
              for (document in it.result.documents) {

                val data = Wallpaper(
                  document.id, -1, document.get("imageurl") as String
                )
                datalist.add(data)
              }
              datalist.shuffle()
              _trendingWallpapers.value = datalist


            } else {
            }
          }
      } catch (e: Exception) {
      }
    }

  }

  fun getCategory() {
    viewModelScope.launch {
      try {

        var datalist: MutableList<Category> = mutableListOf()

        firestore.collection("AOT").document("Category")
          .collection("featured").get()
          .addOnCompleteListener {

            if (it.isSuccessful) {

              for (document in it.result.documents) {

                val data = Category(
                  (document.get("id") as Long).toInt(),
                  document.get("name") as String,
                  document.get("imageurl") as String
                )

                datalist.add(data)
              }
              if(datalist.size!=0)
              _category.value = datalist.get(Random.nextInt(0, 3))

            } else {
            }
          }
      } catch (e: Exception) {
      }
    }
  }

  fun getThreeWallpapers() {
    val datalist: MutableList<Wallpaper> = mutableListOf()
    viewModelScope.launch {
      try {
        firestore.collection("AOT").document("images").collection("threeimages").get()
          .addOnCompleteListener {

            if (it.isSuccessful) {
              for (document in it.result.documents) {

                val data = Wallpaper(
                  document.id, -1, document.get("imageurl") as String
                )
                datalist.add(data)
              }
              _threeWallpapers.value = datalist


            } else {
            }
          }
      } catch (e: Exception) {
      }
    }

  }

  suspend fun isFavorite(id: String): Boolean {
    loadFavourites()
    return favouriteList.any {
      id == it.id

    }

  }

}