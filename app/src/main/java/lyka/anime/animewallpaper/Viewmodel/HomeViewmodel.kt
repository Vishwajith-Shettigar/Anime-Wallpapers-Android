package lyka.anime.animewallpaper.Viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import lyka.anime.animewallpaper.Data.Category
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewmodel(
    private val firestore: FirebaseFirestore
) : ViewModel() {

    private val _categories = MutableStateFlow<MutableList<Category>>(mutableListOf())
    val categories: StateFlow<MutableList<Category>> get() = _categories

    init {
        getCategories()
    }

    fun getCategories() {
        viewModelScope.launch {
            try {

                var datalist: MutableList<Category> = mutableListOf()

                firestore.collection("AOT").document("Category")
                    .collection("category").get()
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
                            _categories.value = datalist

                        } else {
                        }
                    }
            } catch (e: Exception) {
            }
        }
    }
}


