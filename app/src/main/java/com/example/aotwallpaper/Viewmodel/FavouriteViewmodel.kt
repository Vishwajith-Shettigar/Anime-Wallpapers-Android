package com.example.aotwallpaper.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.aotwallpaper.Entity.FavouriteEntity
import com.example.aotwallpaper.Entity.Wallpaper
import com.example.aotwallpaper.Repository.FavouriteRepository
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

class FavouriteViewmodel(
    private val favouriteRepository: FavouriteRepository

) : ViewModel()  {

    private val _favwallpapers = MutableStateFlow<MutableList<FavouriteEntity>>(mutableListOf())
    val favwallpapers: StateFlow<MutableList<FavouriteEntity>> get() = _favwallpapers

    init {

        getAllFavourites()
    }

    private fun getAllFavourites() {
        viewModelScope.launch {
            val datalist=favouriteRepository.getAllFavourites()
            _favwallpapers.value=datalist
        }

    }


}