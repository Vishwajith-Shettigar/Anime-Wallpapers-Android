package lyka.anime.animewallpapers.DI

import android.app.Application
import lyka.anime.animewallpapers.Activities.*
import lyka.anime.animewallpapers.Adapters.CategoryAdapter
import lyka.anime.animewallpapers.Adapters.FavouriteAdapter
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton
import lyka.anime.animewallpapers.Fragments.CategoryFragment
import lyka.anime.animewallpapers.Fragments.FavoriteFragment
import lyka.anime.animewallpapers.Fragments.HomeFragment

@Singleton
@Component(modules = [Appmodule::class, ViewmodelModule::class, DatabaseModule::class])
interface AppComponent {
  fun injectHomeActivity(homeActivity: lyka.anime.animewallpapers.Activities.HomeActivity)

  fun injectHomeFragment(homeFragment: HomeFragment)

  fun injectCategoryFragment(categoryFragment: CategoryFragment)

  fun injectFavoriteFragment(favoriteFragment: FavoriteFragment)

  fun injectCategoryActivity(wallpaperListActivity: WallpaperListActivity)

  fun injectCategoryAdapter(categoryAdapter: CategoryAdapter)

  fun injectWallpaperActivity(wallpaperActivity: WallpaperActivity)

  fun injectFavouriteActivity(favouriteActivity: lyka.anime.animewallpapers.Activities.FavouriteActivity)

  fun injectFavouriteAdapter(favouriteAdapter: FavouriteAdapter)

  fun injectSplashScreenActivity(splashscreenActivity: SplashscreenActivity)


  @Component.Builder
  interface Builder {
    @BindsInstance
    fun applicationContext(context: Application): Builder // Provide the Context instance

    fun build(): AppComponent
  }
}