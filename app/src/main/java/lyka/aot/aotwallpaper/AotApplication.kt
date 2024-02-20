package lyka.aot.aotwallpaper

import android.app.Application
import lyka.aot.aotwallpaper.DI.AppComponent
import lyka.aot.aotwallpaper.DI.DaggerAppComponent

class AotApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = lyka.aot.aotwallpaper.DI.DaggerAppComponent.builder()
            .applicationContext(this)
            .build()



    }


}