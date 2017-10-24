package ru.rougsig.ambercard.features.place.ui.views

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.RelativeLayout
import com.stfalcon.frescoimageviewer.ImageViewer
import kotlinx.android.synthetic.main.gallery_overlay.view.*
import ru.rougsig.ambercard.R

/**
 * Created by rougsig on 24-Oct-17.
 */
class GalleryDialog(val context: Context, val images: List<String>) {
    private val gallery: ImageViewer
    init {
        val overlayView = OverlayView()
        gallery = ImageViewer.Builder(context, images)
                .setOverlayView(overlayView.overlayView)
                .setImageChangeListener(overlayView::onChange)
                .allowSwipeToDismiss(false)
                .build()
    }

    fun show() = gallery.show()
    fun hide() = gallery.onDismiss()

    private inner class OverlayView : RelativeLayout(context) {
        val overlayView = View.inflate(context, R.layout.gallery_overlay, this)!!

        init {
            overlayView.btn_close.setOnClickListener { hide() }
        }

        @SuppressLint("SetTextI18n")
        fun onChange(position: Int) {
            overlayView.counter.text = "${position + 1} из ${images.size}"
        }
    }
}