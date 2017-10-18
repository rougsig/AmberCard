package ru.rougsig.ambercard.utils

import android.Manifest
import android.graphics.Typeface
import android.support.v4.content.ContextCompat
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.rougsig.ambercard.App
import ru.rougsig.ambercard.R
import ru.yandex.yandexmapkit.MapView
import ru.yandex.yandexmapkit.overlay.location.MyLocationItem
import android.Manifest.permission
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.app.Activity
import android.support.v4.app.ActivityCompat
import android.content.pm.PackageManager


/**
 * Created by rougs on 16-Oct-17.
 */

fun <T> Call<T>.enqueue(
        onResponse: (call: Call<T>, response: Response<T>) -> Unit,
        onFailure: (call: Call<T>, t: Throwable) -> Unit
) {
    this.enqueue(object : Callback<T> {
        override fun onResponse(call: Call<T>, response: Response<T>) = onResponse(call, response)
        override fun onFailure(call: Call<T>, t: Throwable) = onFailure(call, t)
    })
}

fun getTitleText(title: Int?, text: String?) = if (text != null && title != null)
    getTitleText(App.context.getString(title) as String, text)
else
    SpannableString("")

fun getTitleText(title: String?, text: String?) = if (text != null && title != null) {
    val spannable = SpannableString(title + " " + text)
    spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(App.context, R.color.black)), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannable.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
    spannable
} else SpannableString("")

fun String?.defaultIfEmptyOrNull(defaultResourceId: Int): String {
    return this.defaultIfEmptyOrNull(ResourceUtils.getString(defaultResourceId))
}

fun String?.defaultIfEmptyOrNull(defaultValue: String): String {
    return if (this.isNullOrEmpty())
        defaultValue
    else this!!
}

fun getMyLocationPermission(activity: Activity, requestCode: Int, granted: () -> Unit) {
    val permACL = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_COARSE_LOCATION)
    val permAFL = ActivityCompat.checkSelfPermission(activity, Manifest.permission.ACCESS_FINE_LOCATION)
    if (permACL != PackageManager.PERMISSION_GRANTED || permAFL != PackageManager.PERMISSION_GRANTED)
        ActivityCompat.requestPermissions(activity, arrayOf(Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION), requestCode)
    else
        granted()
}