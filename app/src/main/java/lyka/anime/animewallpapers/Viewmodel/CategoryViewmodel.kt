package lyka.anime.animewallpapers.Viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.firebase.firestore.DocumentSnapshot
import com.google.firebase.firestore.FieldPath
import lyka.anime.animewallpapers.Entity.Wallpaper
import lyka.anime.animewallpapers.Repository.WallpaperRepository
import lyka.anime.animewallpapers.Util.SharedPreferencesManager
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import lyka.anime.animewallpapers.AppCoroutineScope

class CategoryViewmodel(
  private val firestore: FirebaseFirestore,
  private val wallpaperRepository: WallpaperRepository,
  private val cat_name: String,
  private val cat_id: Int,
) : ViewModel() {
  var lastVisibleDocument: DocumentSnapshot? = null
  var pageSize = 10
  var noPages=false
  private val _wallpapers = MutableStateFlow<MutableList<Wallpaper>>(mutableListOf())
  val wallpapers: StateFlow<MutableList<Wallpaper>> get() = _wallpapers

  init {

    viewModelScope.launch {
      getWallpapers()

    }

  }

  suspend fun insertRoom(datalist: MutableList<Wallpaper>) {
    AppCoroutineScope.scope.launch(IO) {
      if (datalist.isNotEmpty())
        wallpaperRepository.insert(datalist)
    }
  }

  suspend fun getWallpapers() {

    val datalist: MutableList<Wallpaper> = mutableListOf()
    viewModelScope.launch {
      try {
        firestore.collection("AOT").document("images").collection(cat_name).limit(pageSize.toLong())
          .get()
          .addOnCompleteListener {

            if (it.isSuccessful) {

              for (document in it.result.documents) {

                val data = Wallpaper(
                  document.id, cat_id, document.get("imageurl") as String
                )
                datalist.add(data)
              }
              lastVisibleDocument = it.result.documents.last()
              _wallpapers.value = datalist
              viewModelScope.launch {
                insertRoom(datalist)
              }

            } else {
            }
          }
      } catch (e: Exception) {
      }
    }

  }

  suspend fun getMoreWallpapers() {
    val datalist: MutableList<Wallpaper> = mutableListOf()
    viewModelScope.launch {
      try {
        firestore.collection("AOT").document("images").collection(cat_name)
          .orderBy(FieldPath.documentId()).startAfter(lastVisibleDocument!!.id).limit(pageSize.toLong())
          .get()
          .addOnCompleteListener {

            if (it.isSuccessful) {

              for (document in it.result.documents) {

                val data = Wallpaper(
                  document.id, cat_id, document.get("imageurl") as String
                )
                datalist.add(data)
              }
              if(it.result.documents.size!=0)
              lastVisibleDocument = it.result.documents.last()
              else
                noPages=true
              _wallpapers.value = datalist
              viewModelScope.launch {
                insertRoom(datalist)
              }

            } else {
            }
          }
      } catch (e: Exception) {
      }
    }

  }
}