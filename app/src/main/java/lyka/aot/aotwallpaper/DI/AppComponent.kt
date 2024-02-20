package lyka.aot.aotwallpaper.DI

import android.app.Application
import lyka.aot.aotwallpaper.Activities.*
import lyka.aot.aotwallpaper.Adapters.CategoryAdapter
import lyka.aot.aotwallpaper.Adapters.FavouriteAdapter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import lyka.aot.aotwallpaper.Fragments.CategoryFragment
import lyka.aot.aotwallpaper.Fragments.FavoriteFragment
import lyka.aot.aotwallpaper.Fragments.HomeFragment

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