package com.example.aotwallpaper.DI

import android.app.Application
import android.content.Context
import com.example.aotwallpaper.Activities.CategoryActivity
import com.example.aotwallpaper.Activities.HomeActivity
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [Appmodule::class,ViewmodelModule::class,DatabaseModule::class])
interface AppComponent {
    fun injectHomeActivity(homeActivity: HomeActivity)

    fun injectCategoryActivity(categoryActivity: CategoryActivity)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(context: Application): Builder // Provide the Context instance

        fun build(): AppComponent
    }
}