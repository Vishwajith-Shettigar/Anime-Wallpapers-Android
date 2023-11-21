package com.example.aotwallpaper.DI

import androidx.lifecycle.ViewModelProvider
import com.example.aotwallpaper.Viewmodel.CategoryViewmodelFactory
import com.example.aotwallpaper.Viewmodel.WallpaperViewmodelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewmodelModule {

    @Binds
    abstract fun bindcategoryViewModelFactory(factory: CategoryViewmodelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun bindwallpaperViewModelFactory(factory: WallpaperViewmodelFactory): ViewModelProvider.Factory
}