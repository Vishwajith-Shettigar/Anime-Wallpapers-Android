package com.example.aotwallpaper.Viewmodel

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aotwallpaper.Entity.Wallpaper
import com.example.aotwallpaper.Repository.WallpaperRepository
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class CategoryViewmodel(
    private val firestore: FirebaseFirestore,
    private val wallpaperRepository: WallpaperRepository,
    private val cat_name: String,
    private val cat_id: Int
) : ViewModel() {


    private val _wallpapers = MutableStateFlow<MutableList<Wallpaper>>(mutableListOf())
    val wallpapers: StateFlow<MutableList<Wallpaper>> get() = _wallpapers

    private val _roomwallpapers = MutableStateFlow<MutableList<Wallpaper>>(mutableListOf())
    val roomwallpapers: StateFlow<MutableList<Wallpaper>> get() = _roomwallpapers

    init {

        viewModelScope.launch {

            var datalist = wallpaperRepository.getAllWallpapers(cat_id)

            if (datalist.isEmpty()) {
                getWallpapers()

            } else {
                Log.e("#","room call "+cat_id)
                _wallpapers.value = datalist
            }

        }

        viewModelScope.launch {
            roomwallpapers.collect {

                wallpaperRepository.insert(it)
            }
        }

    }

    fun getWallpapers() {


        val datalist: MutableList<Wallpaper> = mutableListOf()
        viewModelScope.launch {
            try {


                firestore.collection("AOT").document("images")
                    .collection(cat_name).get()
                    .addOnCompleteListener {

                        if (it.isSuccessful) {

                            for (document in it.result.documents) {

                                val data = Wallpaper(
                                    document.id,
                                    cat_id,
                                    document.get("imageurl") as String
                                )

                                datalist.add(data)
                            }

                            _wallpapers.value = datalist
                            _roomwallpapers.value = datalist


                        } else {

                        }
                    }

            } catch (e: Exception) {
            }
        }


    }
}