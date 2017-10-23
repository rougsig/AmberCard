package ru.rougsig.ambercard.custom.yandexMap

import android.content.Context
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.ViewParent
import ru.yandex.yandexmapkit.MapView

/**
 * Created by rougs on 18-Oct-17.
 */
class CustomMapView : MapView {
    var viewParent: ViewParent? = null

    constructor(context: Context?, str: String?) : super(context, str)
    constructor(context: Context?, attrs: AttributeSet?) : super(context, attrs)

    override fun onInterceptTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN ->
                if (null == viewParent)
                    parent.requestDisallowInterceptTouchEvent(true)
                else
                    viewParent!!.requestDisallowInterceptTouchEvent(true)
            MotionEvent.ACTION_UP ->
                if (null == viewParent)
                    parent.requestDisallowInterceptTouchEvent(false)
                else
                    viewParent!!.requestDisallowInterceptTouchEvent(false)
        }
        return super.onInterceptTouchEvent(event)
    }
}