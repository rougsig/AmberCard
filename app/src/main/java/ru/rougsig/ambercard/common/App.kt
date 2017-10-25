package ru.rougsig.ambercard.common

import android.app.Application
import com.evernote.android.job.JobManager
import com.facebook.drawee.backends.pipeline.Fresco
import com.facebook.imagepipeline.core.ImagePipelineConfig
import com.facebook.imagepipeline.decoder.SimpleProgressiveJpegConfig
import io.realm.Realm
import io.realm.RealmConfiguration
import ru.rougsig.ambercard.common.db.Migration
import ru.rougsig.ambercard.common.di.AppComponent
import ru.rougsig.ambercard.common.di.DaggerAppComponent
import ru.rougsig.ambercard.common.di.modules.ContextModule
import ru.rougsig.ambercard.common.jobs.JobCreator
import ru.rougsig.ambercard.common.jobs.PlaceSyncJob


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

        JobManager.create(this).addJobCreator(JobCreator())
        PlaceSyncJob.scheduleJob()
    }
}