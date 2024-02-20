package lyka.aot.animewallpapers

import android.app.Application
import lyka.aot.animewallpapers.DI.AppComponent
import lyka.aot.animewallpapers.DI.DaggerAppComponent

class AotApplication : Application() {
    lateinit var appComponent: AppComponent

    override fun onCreate() {
        super.onCreate()
        appComponent = lyka.aot.animewallpapers.DI.DaggerAppComponent.builder()
            .applicationContext(this)
            .build()



    }


}