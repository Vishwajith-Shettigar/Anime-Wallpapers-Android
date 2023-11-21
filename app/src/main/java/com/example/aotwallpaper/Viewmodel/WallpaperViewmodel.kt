package com.example.aotwallpaper.Viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aotwallpaper.Data.Category
import com.example.aotwallpaper.Data.Wallpaper
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class WallpaperViewmodel(
    private val firestore: FirebaseFirestore,
    private val cat_name:String
):ViewModel() {

    private val _wallpapers = MutableStateFlow<MutableList<Wallpaper>>(mutableListOf())
    val wallpapers: StateFlow<MutableList<Wallpaper>> get() = _wallpapers

    init{
        getWallpapers()
    }
    fun getWallpapers(){

        viewModelScope.launch {
            try {

                var datalist: MutableList<Wallpaper> = mutableListOf()

                firestore.collection("AOT").document("images")
                    .collection(cat_name).get()
                    .addOnCompleteListener {

                        if (it.isSuccessful) {

                            for (document in it.result.documents) {

                                val data = Wallpaper(

                                    document.get("imageurl") as String
                                )

                                datalist.add(data)
                            }

                            _wallpapers.value = datalist

                        } else {

                        }
                    }
            } catch (e: Exception) {
            }
        }

    }
}