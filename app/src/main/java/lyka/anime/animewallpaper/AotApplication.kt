package lyka.anime.animewallpaper

import android.app.Application
import lyka.anime.animewallpaper.DI.AppComponent

class AotApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = lyka.anime.animewallpaper.DI.DaggerAppComponent.builder()
            .applicationContext(this)
            .build()



    }


}