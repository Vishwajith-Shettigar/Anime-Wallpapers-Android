package lyka.anime.animewallpaper.DI

import androidx.lifecycle.ViewModelProvider
import lyka.anime.animewallpaper.Viewmodel.HomeViewmodelFactory
import lyka.anime.animewallpaper.Viewmodel.CategoryViewmodelFactory
import lyka.anime.animewallpaper.Viewmodel.FavouriteViewmodelFactory
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