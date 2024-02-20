package lyka.aot.animewallpapers.DI

import androidx.lifecycle.ViewModelProvider
import lyka.aot.animewallpapers.Viewmodel.HomeViewmodelFactory
import lyka.aot.animewallpapers.Viewmodel.CategoryViewmodelFactory
import lyka.aot.animewallpapers.Viewmodel.FavouriteViewmodelFactory
import dagger.Binds
import dagger.Module
import lyka.aot.animewallpapers.Viewmodel.FeatureAndTrendingViewmodelFactory

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