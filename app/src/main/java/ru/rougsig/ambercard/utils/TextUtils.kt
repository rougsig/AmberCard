package ru.rougsig.ambercard.utils

import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.ForegroundColorSpan
import android.text.style.StyleSpan
import ru.rougsig.ambercard.common.App

/**
 * Created by rougs on 23-Oct-17.
 */

object TextUtils {
    fun getTitleText(title: String, text: String, color: Int): SpannableString {
        val spannable = SpannableString(title + " " + text)
        spannable.setSpan(ForegroundColorSpan(color), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        return spannable
    }
}

fun String?.defaultIfEmptyOrNull(defaultResourceId: Int): String {
    return this.defaultIfEmptyOrNull(App.appComponent.getContext().getString(defaultResourceId))
}

fun String?.defaultIfEmptyOrNull(defaultValue: String): String {
    return if (this.isNullOrEmpty())
        defaultValue
    else this!!
}