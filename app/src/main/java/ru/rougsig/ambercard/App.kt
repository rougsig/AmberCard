package ru.rougsig.ambercard

import android.app.Application
import android.content.Context
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.rougsig.ambercard.common.DB.Migration
import ru.yandex.yandexmapkit.utils.GeoPoint


/**
 * Created by rougs on 12-Oct-17.
 */

class App : Application() {
    override fun onCreate() {
        super.onCreate()
        instance = this
        Fresco.initialize(
                this,
                ImagePipelineConfig.newBuilder(this)
                        .setProgressiveJpegConfig(SimpleProgressiveJpegConfig())
                        .setResizeAndRotateEnabledForNetwork(true)
                        .setDownsampleEnabled(true)
                        .build()
        )

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

        var myLocation: GeoPoint? = null
    }
}