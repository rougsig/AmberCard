package ru.rougsig.ambercard

import android.app.Application
import android.content.Context

/**
 * Created by rougs on 12-Oct-17.
 */
class App : Application() {
    override fun onCreate() {
        instance = this
        super.onCreate()
    }

    companion object {
        lateinit var instance: App
            private set

        val context: Context
            get() = instance
    }
}