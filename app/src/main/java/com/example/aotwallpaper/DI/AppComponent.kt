package com.example.aotwallpaper.DI

import com.example.aotwallpaper.Activities.CategoryActivity
import com.example.aotwallpaper.Activities.HomeActivity
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [Appmodule::class,ViewmodelModule::class])
interface AppComponent {
    fun injectHomeActivity(homeActivity: HomeActivity)

    fun injectCategoryActivity(categoryActivity: CategoryActivity)
}