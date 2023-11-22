package com.example.aotwallpaper.DI

import androidx.lifecycle.ViewModelProvider
import com.example.aotwallpaper.Viewmodel.HomeViewmodelFactory
import com.example.aotwallpaper.Viewmodel.CategoryViewmodelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewmodelModule {

    @Binds
    abstract fun bindcategoryViewModelFactory(factory: HomeViewmodelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun bindwallpaperViewModelFactory(factory: CategoryViewmodelFactory): ViewModelProvider.Factory
}