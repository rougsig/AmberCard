package ru.rougsig.ambercard.custom.gallery

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.ImageButton
import android.widget.RelativeLayout
import android.widget.TextView
import com.stfalcon.frescoimageviewer.ImageViewer
import mu.KotlinLogging
import ru.rougsig.ambercard.R


/**
 * Created by rougs on 19-Oct-17.
 */

@SuppressLint("SetTextI18n")
class GalleryView(context: Context, val maxCount: Int) : RelativeLayout(context) {
    private val logger = KotlinLogging.logger("GalleryView")

    var gallery: ImageViewer? = null
    private val view = View.inflate(context, R.layout.view_gallery, this)!!
    private val close = view.findViewById<ImageButton>(R.id.btn_close)!!
    private val counter = view.findViewById<TextView>(R.id.counter)!!

    init {
        counter.text = "1 из $maxCount"
        close.setOnClickListener {
            if (gallery != null) {
                gallery!!.onDismiss()
            } else {
                logger.error("setGallery to GalleryView", IllegalStateException("gallery is null"))
            }
        }
    }

    fun changeListener(pos: Int) {
        counter.text = "${pos + 1} из $maxCount"
    }
}