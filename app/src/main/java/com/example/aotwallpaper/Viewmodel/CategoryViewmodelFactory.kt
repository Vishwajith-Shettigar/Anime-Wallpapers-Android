package com.example.aotwallpaper.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class CategoryViewmodelFactory @Inject constructor(
    private val firestore: FirebaseFirestore

):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewmodel(firestore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}