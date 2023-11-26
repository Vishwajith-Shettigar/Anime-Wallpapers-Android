package com.example.aotwallpaper.DI

import android.app.Application
import com.example.aotwallpaper.Util.SharedPreferencesManager
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

    @Singleton
    @Provides
    fun getSharedPreferenceManager(context: Application):SharedPreferencesManager
    {
        return SharedPreferencesManager(context)
    }
}