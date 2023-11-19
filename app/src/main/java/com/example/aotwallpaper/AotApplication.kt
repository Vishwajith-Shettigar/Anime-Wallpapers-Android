package com.example.aotwallpaper

import android.app.Application
import com.example.aotwallpaper.DI.AppComponent
import com.example.aotwallpaper.DI.DaggerAppComponent

class AotApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent.builder()
            .build()

    }
}