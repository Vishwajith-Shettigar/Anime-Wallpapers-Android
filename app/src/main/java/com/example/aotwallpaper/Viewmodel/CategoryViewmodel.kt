package com.example.aotwallpaper.Viewmodel

import android.app.Application
import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.aotwallpaper.Data.Category
import com.google.android.gms.tasks.OnCompleteListener
import com.google.firebase.firestore.FirebaseFirestore
import javax.inject.Inject

class CategoryViewmodel(
   private  val firestore: FirebaseFirestore
) :ViewModel(){

   fun getCategories(){
      firestore.collection("AOT").document("category")
         .get()
         .addOnCompleteListener{
            if(it.isSuccessful){
               for (document in it.result.id){
                  Log.e("#",document.category.name)

               }
            }
         }

   }

}