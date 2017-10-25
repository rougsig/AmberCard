package ru.rougsig.ambercard.utils

import android.annotation.SuppressLint
import android.content.Context
import com.github.debop.kodatimes.days
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import org.joda.time.DateTime
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.App


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