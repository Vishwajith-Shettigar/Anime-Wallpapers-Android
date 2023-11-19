package com.example.aotwallpaper.DI

import com.google.firebase.firestore.FirebaseFirestore
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class Appmodule {

    @Singleton
    @Provides
    fun getFiebaseFirestore():FirebaseFirestore
    {
        return FirebaseFirestore.getInstance()
    }
}