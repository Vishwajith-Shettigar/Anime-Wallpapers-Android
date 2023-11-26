package com.example.aotwallpaper.DI

import android.app.Application
import android.content.Context
import com.example.aotwallpaper.Activities.CategoryActivity
import com.example.aotwallpaper.Activities.FavouriteActivity
import com.example.aotwallpaper.Activities.HomeActivity
import com.example.aotwallpaper.Activities.WallpaperActivity
import com.example.aotwallpaper.Adapters.CategoryAdapter
import com.example.aotwallpaper.Adapters.FavouriteAdapter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [Appmodule::class,ViewmodelModule::class,DatabaseModule::class])
interface AppComponent {
    fun injectHomeActivity(homeActivity: HomeActivity)

    fun injectCategoryActivity(categoryActivity: CategoryActivity)

    fun injectCategoryAdapter(categoryAdapter: CategoryAdapter)

    fun injectWallpaperActivity(wallpaperActivity: WallpaperActivity)

    fun injectFavouriteActivity(favouriteActivity: FavouriteActivity)

    fun injectFavouriteAdapter(favouriteAdapter: FavouriteAdapter)

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun applicationContext(context: Application): Builder // Provide the Context instance

        fun build(): AppComponent
    }
}