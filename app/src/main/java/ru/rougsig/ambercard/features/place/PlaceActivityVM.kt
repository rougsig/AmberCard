package ru.rougsig.ambercard.features.place

import android.content.pm.PackageManager
import android.databinding.ObservableBoolean
import android.databinding.ObservableField
import android.location.Location
import android.view.View
import android.widget.Toast
import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel
import com.stfalcon.frescoimageviewer.ImageViewer
import ru.rougsig.ambercard.App
import ru.rougsig.ambercard.App.Companion.context
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.API.REST
import ru.rougsig.ambercard.custom.gallery.GalleryView
import ru.rougsig.ambercard.custom.yandexMap.MapController
import ru.rougsig.ambercard.features.place.data.PlaceModel
import ru.rougsig.ambercard.features.place.data.PlaceRepository
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
    private lateinit var gallery: ImageViewer

    override fun onStart() {
        super.onStart()
        PlaceRepository.getPlaceById(
                activity.intent.extras.getInt(PlaceActivity.EXTRA_ID),
                this::onLoadedSuccess,
                this::onLoadedFailure
        )
        if (App.myLocation == null) {
            getMyLocationPermission(activity, PERMISSIONS_CODE, { mapOverlayManager.myLocation.refreshPermission() })
            mapOverlayManager.myLocation.addMyLocationListener { myLocation ->
                App.myLocation = myLocation.geoPoint!!
                calcDistance(myLocation.geoPoint)
                mapController.setPositionAnimationTo(point, 14f)
            }
        } else {
            calcDistance(App.myLocation!!)
        }
    }

    private fun calcDistance(myLocation: GeoPoint) {
        val result = FloatArray(3)
        Location.distanceBetween(
                place.get().latitude!!,
                place.get().longitude!!,
                myLocation.lat,
                myLocation.lon,
                result
        )
        distance.set((result[0] / 1000).toString().substring(0, 3) + " км")
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

        // FIXME START
        val galleryView = GalleryView(activity, place.photos.size)
        gallery = ImageViewer.Builder(
                activity,
                ArrayList<String>().apply {
                    place.photos.forEach { url ->
                        this.add(REST.baseURL + url)
                    }
                })
                .setImageChangeListener(galleryView::changeListener)
                .setOverlayView(galleryView)
                .build()
        galleryView.gallery = gallery
        // FIXME END
        inLoading.set(false)
    }

    private fun onLoadedFailure() {
        inLoading.set(false)
        Toast.makeText(activity, context.getString(R.string.error_wtf), Toast.LENGTH_SHORT).show()
    }

    fun onClickBack(view: View) = activity.finish()
    fun onClickGallery(view: View) = gallery.show()

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PERMISSIONS_CODE && grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)
            mapOverlayManager.myLocation.refreshPermission()
    }
}