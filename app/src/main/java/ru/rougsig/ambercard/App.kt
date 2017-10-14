package ru.rougsig.ambercard

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco

/**
 * Created by rougs on 12-Oct-17.
 */
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        Fresco.initialize(this);
    }

    companion object {
        lateinit var instance: App
            private set

        val context: Context
            get() = instance
    }
}