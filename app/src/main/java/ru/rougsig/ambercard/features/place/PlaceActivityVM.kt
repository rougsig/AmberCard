package ru.rougsig.ambercard.features.place

import android.content.pm.PackageManager
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.databinding.ObservableFloat
import android.databinding.ObservableInt
import android.location.Location
import android.view.View
import android.widget.Toast
import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel
import ru.rougsig.ambercard.App.Companion.context
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.features.place.data.PlaceModel
import ru.rougsig.ambercard.features.place.data.PlaceRepository
import ru.rougsig.ambercard.custom.yandexMap.MapController
import ru.rougsig.ambercard.utils.ResourceUtils.getDrawable
import ru.rougsig.ambercard.utils.getMyLocationPermission
import ru.yandex.yandexmapkit.overlay.Overlay
import ru.yandex.yandexmapkit.overlay.OverlayItem
import ru.yandex.yandexmapkit.utils.GeoPoint

/**
 * Created by rougs on 11-Oct-17.
 */

class PlaceActivityVM(activity: PlaceActivity) : ActivityViewModel<PlaceActivity>(activity) {
    val place = ObservableField<PlaceModel>()
    val distance = ObservableField<String>()
    val address = ObservableField<String>()
    val inLoading = ObservableBoolean(true)
    private lateinit var point: GeoPoint
    private val map = activity.binding.map
    private val mapController = MapController(map)
    private val mapOverlayManager = mapController.overlayManager
    private val mapOverlay = Overlay(map.mapController)
    private val PERMISSIONS_CODE = 1

    override fun onStart() {
        super.onStart()
        PlaceRepository.getPlaceById(
                activity.intent.extras.getInt(PlaceActivity.EXTRA_ID),
                this::onLoadedSuccess,
                this::onLoadedFailure
        )
        mapOverlayManager.myLocation.addMyLocationListener { myLocation ->
            val result = FloatArray(3)
            Location.distanceBetween(
                    place.get().latitude!!,
                    place.get().longitude!!,
                    myLocation.geoPoint.lat,
                    myLocation.geoPoint.lon,
                    result
            )
            distance.set((result[0] / 1000).toString().substring(0, 3) + " км")
            mapController.setPositionAnimationTo(point, 14f)
        }
        getMyLocationPermission(activity, PERMISSIONS_CODE, { mapOverlayManager.myLocation.refreshPermission() })
    }

    override fun onResume() {
        activity.binding.scroll.scrollTo(0, 0)
    }

    private fun onLoadedSuccess(place: PlaceModel) {
        this.place.set(place)
        point = GeoPoint(
                place.latitude!!,
                place.longitude!!
        )
        mapOverlay.addOverlayItem(
                OverlayItem(
                        point,
                        getDrawable(R.drawable.ic_pin))
        )
        mapOverlayManager.addOverlay(mapOverlay)
        mapController.setPositionAnimationTo(point, 14f)
        map.viewParent = activity.binding.scroll
        mapController.downloader.getGeoCode(
                { geoCode ->
                    if (geoCode != null) {
                        address.set(geoCode.title)
                        true
                    } else
                        false
                },
                point
        )
        inLoading.set(false)
    }

    private fun onLoadedFailure() {
        inLoading.set(false)
        Toast.makeText(activity, context.getString(R.string.error_wtf), Toast.LENGTH_SHORT).show()
    }

    fun onClickBack(view: View) = activity.finish()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        when (requestCode) {
            PERMISSIONS_CODE -> if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
                mapOverlayManager.myLocation.refreshPermission()
            else -> {
            }
        }
    }
}