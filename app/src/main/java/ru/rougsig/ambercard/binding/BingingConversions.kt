package ru.rougsig.ambercard.binding

import android.view.View.GONE
import android.databinding.BindingConversion
import android.view.View


/**
 * Created by rougsig on 13-Oct-17.
 */
object BindingConversions {
    @BindingConversion
    fun convertBooleanToVisibility(visible: Boolean): Int = if (visible) View.VISIBLE else View.GONE
}