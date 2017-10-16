package ru.rougsig.ambercard.binding

import android.databinding.BindingConversion
import android.view.View
import com.facebook.drawee.view.SimpleDraweeView
import android.databinding.BindingAdapter


/**
 * Created by rougsig on 13-Oct-17.
 */
object BindingAdapters {
    @JvmStatic
    @BindingConversion
    fun convertBooleanToVisibility(visible: Boolean): Int = if (visible) View.VISIBLE else View.GONE

    @JvmStatic
    @BindingAdapter("app:imageUrl")
    fun loadImage(view: SimpleDraweeView, imageUrl: String?) {
        if (!imageUrl.isNullOrEmpty())
            view.setImageURI(RESTConst.baseURL + imageUrl)
    }
}