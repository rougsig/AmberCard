package ru.rougsig.ambercard.binding

import android.view.View

/**
 * Created by rougs on 14-Oct-17.
 */
object TypeConverter {
    @JvmStatic
    fun toVisibility(visible: String?) = if (!visible.isNullOrEmpty()) View.VISIBLE else View.GONE
}