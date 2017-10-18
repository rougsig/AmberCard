package ru.rougsig.ambercard.features.place

import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.view.View
import android.widget.Toast
import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel
import ru.rougsig.ambercard.App.Companion.context
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.features.place.data.PlaceModel
import ru.rougsig.ambercard.features.place.data.PlaceRepository
import ru.rougsig.ambercard.custom.yandexMap.MapController
import ru.rougsig.ambercard.utils.ResourceUtils.getDrawable
import ru.yandex.yandexmapkit.overlay.Overlay
import ru.yandex.yandexmapkit.overlay.OverlayItem
import ru.yandex.yandexmapkit.utils.GeoPoint

/**
 * Created by rougs on 11-Oct-17.
 */

class PlaceActivityVM(activity: PlaceActivity) : ActivityViewModel<PlaceActivity>(activity) {
    val place = ObservableField<PlaceModel>()
    val inLoading = ObservableBoolean(true)
    private val map = activity.binding.map
    private val mapController = MapController(map)
    private val mapOverlayManager = mapController.overlayManager
    private val mapOverlay = Overlay(map.mapController)
    override fun onStart() {
        super.onStart()
        PlaceRepository.getPlaceById(
                activity.intent.extras.getInt(PlaceActivity.EXTRA_ID),
                this::onLoadedSuccess,
                this::onLoadedFailure
        )
        activity.binding.scroll.requestDisallowInterceptTouchEvent(true)
    }

    private fun onLoadedSuccess(place: PlaceModel) {
        this.place.set(place)
        val point = GeoPoint(
                place.latitude!!,
                place.longitude!!
        )
        mapOverlay.addOverlayItem(
                OverlayItem(
                        point,
                        getDrawable(R.drawable.ic_pin))
        )
        mapController.overlayManager.addOverlay(mapOverlay)
        mapController.setPositionAnimationTo(point, 14f)
        map.viewParent = activity.binding.scroll
        inLoading.set(false)
    }

    private fun onLoadedFailure() {
        inLoading.set(false)
        Toast.makeText(activity, context.getString(R.string.error_wtf), Toast.LENGTH_SHORT).show()
    }

    fun onClickBack(view: View) = activity.finish()
}