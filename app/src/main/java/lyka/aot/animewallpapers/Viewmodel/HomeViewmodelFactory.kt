package lyka.aot.animewallpapers.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class HomeViewmodelFactory @Inject constructor(
    private val firestore: FirebaseFirestore

):ViewModelProvider.Factory {

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(HomeViewmodel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return HomeViewmodel(firestore) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}