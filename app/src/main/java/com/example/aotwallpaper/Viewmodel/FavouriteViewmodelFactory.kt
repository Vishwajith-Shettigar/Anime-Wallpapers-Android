package com.example.aotwallpaper.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.aotwallpaper.Repository.FavouriteRepository
import com.google.firebase.firestore.FirebaseFirestore
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