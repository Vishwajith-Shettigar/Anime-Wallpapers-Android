package lyka.anime.animewallpapers

import android.app.Application
import lyka.anime.animewallpapers.DI.AppComponent
import lyka.anime.animewallpapers.DI.DaggerAppComponent

class animeApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = lyka.anime.animewallpapers.DI.DaggerAppComponent.builder()
            .applicationContext(this)
            .build()



    }


}