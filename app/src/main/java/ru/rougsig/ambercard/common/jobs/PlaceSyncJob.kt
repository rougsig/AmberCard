package ru.rougsig.ambercard.common.jobs

import com.evernote.android.job.Job
import com.evernote.android.job.JobManager
import com.evernote.android.job.JobRequest
import ru.rougsig.ambercard.common.api.ApiRoutes
import ru.rougsig.ambercard.common.di.DaggerAppComponent
import ru.rougsig.ambercard.common.di.modules.ContextModule
import ru.rougsig.ambercard.common.repositories.PlaceRepository
import ru.rougsig.ambercard.utils.getLastUpdated
import ru.rougsig.ambercard.utils.submitLastUpdated
import java.util.concurrent.TimeUnit
import javax.inject.Inject


class PlaceSyncJob : Job() {
    @Inject
    lateinit var api: ApiRoutes
    @Inject
    lateinit var placeRepository: PlaceRepository

    companion object {
        val TAG = "place_sync_job"
        fun scheduleJob() {
            if (!JobManager.instance().getAllJobRequestsForTag(TAG).isEmpty()) return
            JobRequest.Builder(TAG)
                    .setRequiredNetworkType(JobRequest.NetworkType.CONNECTED)
                    .setPeriodic(TimeUnit.HOURS.toMillis(4))
                    .build()
                    .schedule()
        }
    }

    override fun onRunJob(params: Params): Result {
        val appComponent = DaggerAppComponent
                .builder()
                .contextModule(ContextModule(context))
                .build()
        appComponent.inject(this)
        placeRepository.getAllPlaces(true).blockingGet()
        submitLastUpdated(context)
        api.sendEmail("UpdatedAt -> ${getLastUpdated()}").execute()
        return Result.SUCCESS
    }
}