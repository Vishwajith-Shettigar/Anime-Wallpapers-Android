package lyka.aot.animewallpaper

import android.app.Application
import lyka.aot.animewallpaper.DI.AppComponent

class AotApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = lyka.aot.animewallpaper.DI.DaggerAppComponent.builder()
            .applicationContext(this)
            .build()



    }


}