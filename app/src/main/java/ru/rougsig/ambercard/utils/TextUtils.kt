package ru.rougsig.ambercard.utils

import android.support.v4.content.ContextCompat
import android.text.style.ForegroundColorSpan
import ru.rougsig.ambercard.R
import android.graphics.Typeface
import android.text.Spannable
import android.text.SpannableString
import android.text.style.StyleSpan
import ru.rougsig.ambercard.App


/**
 * Created by rougs on 11-Oct-17.
 */
object TextUtils {
    @JvmStatic
    fun getTitleText(title: Int?, text: String?) = if (text != null && title != null)
        getTitleText(App.context.getString(title) as String, text)
    else
        SpannableString("")

    @JvmStatic
    fun getTitleText(title: String?, text: String?) = if (text != null && title != null) {
        val spannable = SpannableString(title + " " + text)
        spannable.setSpan(ForegroundColorSpan(ContextCompat.getColor(App.context, R.color.black)), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, Spannable.SPAN_EXCLUSIVE_EXCLUSIVE)
        spannable
    } else SpannableString("")
}