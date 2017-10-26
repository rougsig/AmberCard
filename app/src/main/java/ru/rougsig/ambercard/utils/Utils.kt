package ru.rougsig.ambercard.utils

import android.annotation.SuppressLint
import android.content.Context
import android.location.Location
import com.github.debop.kodatimes.days
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.App
import ru.rougsig.ambercard.common.di.modules.LocationModule


/**
 * Created by rougs on 21-Oct-17.
 */

fun <T> Single<T>.applySchedulers() = this.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())!!

fun getLastUpdated(context: Context = App.appComponent.getContext()): DateTime {
    val pref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE)
    return DateTime(pref.getString("updatedAt", (DateTime.now() - 1.days()).toString()))
}

@SuppressLint("CommitPrefEdits")
fun submitLastUpdated(context: Context = App.appComponent.getContext()) {
    val pref = context.getSharedPreferences(context.getString(R.string.preference_file_key), Context.MODE_PRIVATE).edit()
    pref.putString("updatedAt", DateTime.now().toString())
}

fun formatDistance(distance: Float): String {
    return if (distance >= 1000)
        (distance.toInt() / 1000).toString().plus("км")
    else
        distance.toInt().toString().plus("м")
}

fun getDistance(latitude: Double, longitude: Double): String {
    return if (LocationModule.location == null)
        ""
    else
        formatDistance(LocationModule
                .location!!
                .distanceTo(
                        Location("").apply {
                            this.latitude = latitude
                            this.longitude = longitude
                        }
                ))
}

fun checkDateTime(dateTime: DateTime): Boolean {
    val now = DateTime.now()
    return now.year > dateTime.year || now.monthOfYear > dateTime.monthOfYear || now.dayOfMonth > dateTime.dayOfMonth || (now.hourOfDay - 4) > dateTime.hourOfDay
}