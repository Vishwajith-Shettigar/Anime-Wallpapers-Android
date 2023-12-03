package lyka.aot.aotwallpaper.DI

import androidx.lifecycle.ViewModelProvider
import lyka.aot.aotwallpaper.Viewmodel.HomeViewmodelFactory
import lyka.aot.aotwallpaper.Viewmodel.CategoryViewmodelFactory
import lyka.aot.aotwallpaper.Viewmodel.FavouriteViewmodelFactory
import dagger.Binds
import dagger.Module

@Module
abstract class ViewmodelModule {

    @Binds
    abstract fun bindcategoryViewModelFactory(factory: HomeViewmodelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun bindwallpaperViewModelFactory(factory: CategoryViewmodelFactory): ViewModelProvider.Factory

    @Binds
    abstract fun bindFavouriteViewModelFactory(factory: FavouriteViewmodelFactory): ViewModelProvider.Factory
}