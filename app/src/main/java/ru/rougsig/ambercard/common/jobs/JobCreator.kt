package ru.rougsig.ambercard.common.jobs

import android.content.Context
import com.evernote.android.job.Job
import com.evernote.android.job.JobCreator
import com.evernote.android.job.JobManager


/**
 * Created by rougs on 24-Oct-17.
 */
class JobCreator : JobCreator {
    override fun create(tag: String): Job? {
        return when (tag) {
            PlaceSyncJob.TAG -> PlaceSyncJob()
            else -> null
        }
    }
}