package ru.rougsig.ambercard

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.rougsig.ambercard.common.DB.Migration


/**
 * Created by rougs on 12-Oct-17.
 */

// TODO кэш изображений
class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        Fresco.initialize(this)

        Realm.init(this)
        Realm.setDefaultConfiguration(RealmConfiguration.Builder()
                .name("database.realm")
                .schemaVersion(1)
                .migration(Migration())
                .build())
    }

    companion object {
        lateinit var instance: App
            private set

        val context: Context
            get() = instance
    }
}