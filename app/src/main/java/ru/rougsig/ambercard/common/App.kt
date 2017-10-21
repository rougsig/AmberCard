package ru.rougsig.ambercard.common

import android.app.Application
import ru.rougsig.ambercard.common.di.AppComponent
import ru.rougsig.ambercard.common.di.DaggerAppComponent
import ru.rougsig.ambercard.common.di.modules.ContextModule


/**
 * Created by rougs on 21-Oct-17.
 */
class App : Application() {
    companion object {
        lateinit var appComponent: AppComponent
    }

    override fun onCreate() {
        super.onCreate()
        appComponent = DaggerAppComponent
                .builder()
                .contextModule(ContextModule(this))
                .build()
    }
}