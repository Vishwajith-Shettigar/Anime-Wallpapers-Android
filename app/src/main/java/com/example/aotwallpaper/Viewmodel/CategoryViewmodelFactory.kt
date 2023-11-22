package com.example.aotwallpaper.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class CategoryViewmodelFactory @Inject constructor(
    private val firestore: FirebaseFirestore,



):ViewModelProvider.Factory {
     lateinit var cat_name:String
    fun setCatName(catName: String) {
        this.cat_name = catName
    }

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(CategoryViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return CategoryViewmodel(firestore,cat_name) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}