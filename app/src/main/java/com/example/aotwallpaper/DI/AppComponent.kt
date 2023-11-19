package com.example.aotwallpaper.DI

import com.example.aotwallpaper.Activities.HomeActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [Appmodule::class])
interface AppComponent {
    fun injectHomeActivity(homeActivity: HomeActivity)
}