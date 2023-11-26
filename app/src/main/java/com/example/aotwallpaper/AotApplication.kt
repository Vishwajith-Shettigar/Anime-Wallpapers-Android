package com.example.aotwallpaper

import android.app.Application
import android.util.Log
import com.example.aotwallpaper.DI.AppComponent
import com.example.aotwallpaper.DI.DaggerAppComponent
import com.example.aotwallpaper.Repository.WallpaperRepository
import javax.inject.Inject

class AotApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .applicationContext(this)
            .build()



    }


}