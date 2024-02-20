package lyka.aot.animewallpapers.DI

import android.app.Application
import lyka.aot.animewallpapers.Activities.*
import lyka.aot.animewallpapers.Adapters.CategoryAdapter
import lyka.aot.animewallpapers.Adapters.FavouriteAdapter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import lyka.aot.animewallpapers.Fragments.CategoryFragment
import lyka.aot.animewallpapers.Fragments.FavoriteFragment
import lyka.aot.animewallpapers.Fragments.HomeFragment

@Singleton
@Component(modules = [Appmodule::class, ViewmodelModule::class, DatabaseModule::class])
interface AppComponent {
  fun injectHomeActivity(homeActivity: HomeActivity)

  fun injectHomeFragment(homeFragment: HomeFragment)

  fun injectCategoryFragment(categoryFragment: CategoryFragment)

  fun injectFavoriteFragment(favoriteFragment: FavoriteFragment)

  fun injectCategoryActivity(wallpaperListActivity: WallpaperListActivity)

  fun injectCategoryAdapter(categoryAdapter: CategoryAdapter)

  fun injectWallpaperActivity(wallpaperActivity: WallpaperActivity)

  fun injectFavouriteActivity(favouriteActivity: FavouriteActivity)

  fun injectFavouriteAdapter(favouriteAdapter: FavouriteAdapter)

  fun injectSplashScreenActivity(splashscreenActivity: SplashscreenActivity)


  @Component.Builder
  interface Builder {
    @BindsInstance
    fun applicationContext(context: Application): Builder // Provide the Context instance

    fun build(): AppComponent
  }
}