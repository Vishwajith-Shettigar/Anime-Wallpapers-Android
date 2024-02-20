package lyka.anime.animewallpapers.DI

import androidx.lifecycle.ViewModelProvider
import lyka.anime.animewallpapers.Viewmodel.HomeViewmodelFactory
import lyka.anime.animewallpapers.Viewmodel.CategoryViewmodelFactory
import lyka.anime.animewallpapers.Viewmodel.FavouriteViewmodelFactory
import dagger.Binds
import dagger.Module
import lyka.anime.animewallpapers.Viewmodel.FeatureAndTrendingViewmodelFactory

@Module
abstract class ViewmodelModule {

  @Binds
  abstract fun bindcategoryViewModelFactory(factory: HomeViewmodelFactory): ViewModelProvider.Factory

  @Binds
  abstract fun bindwallpaperViewModelFactory(factory: CategoryViewmodelFactory): ViewModelProvider.Factory

  @Binds
  abstract fun bindFavouriteViewModelFactory(factory: FavouriteViewmodelFactory): ViewModelProvider.Factory

  @Binds
  abstract fun bindFeatureAndTrendingViewmodelFactory(factory: FeatureAndTrendingViewmodelFactory): ViewModelProvider.Factory
}