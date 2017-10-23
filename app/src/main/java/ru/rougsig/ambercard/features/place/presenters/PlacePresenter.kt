package ru.rougsig.ambercard.features.place.presenters

import android.location.Location
import android.support.v4.content.ContextCompat.getDrawable
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import retrofit2.HttpException
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.App
import ru.rougsig.ambercard.common.custom.yandexMap.MapController
import ru.rougsig.ambercard.common.repositories.PlaceRepository
import ru.rougsig.ambercard.custom.yandexMap.CustomMapView
import ru.rougsig.ambercard.features.place.models.PlaceModel
import ru.rougsig.ambercard.features.place.views.PlaceView
import ru.yandex.yandexmapkit.overlay.Overlay
import ru.yandex.yandexmapkit.overlay.OverlayItem
import ru.yandex.yandexmapkit.utils.GeoPoint
import java.io.IOException
import javax.inject.Inject

/**
 * Created by rougs on 23-Oct-17.
 */
@InjectViewState
class PlacePresenter : MvpPresenter<PlaceView>() {
    @Inject
    lateinit var placeRepository: PlaceRepository
    private lateinit var place: PlaceModel
    private var address: String = ""
    private var allowTracking = false
    private lateinit var map: CustomMapView

    fun initMap(map: CustomMapView) {
        val mc = MapController(map)
        val point = GeoPoint(
                place.latitude!!,
                place.longitude!!
        )
        val mapOverlay = Overlay(map.mapController)
        mapOverlay.addOverlayItem(
                OverlayItem(
                        point,
                        getDrawable(map.context, R.drawable.ic_pin))
        )
        mc.overlayManager.addOverlay(mapOverlay)
        mc.setPositionAnimationTo(point, 14f)
    }

    fun allowMapTracking(map: CustomMapView) {
        this@PlacePresenter.map = map
        allowTracking = true
    }

    private fun startPositionTracking(map: CustomMapView) {
        val mc = MapController(map)
        mc.overlayManager.myLocation.refreshPermission()
        val point = GeoPoint(
                place.latitude!!,
                place.longitude!!
        )
        mc.downloader.getGeoCode(
                { geoCode ->
                    if (geoCode != null) {
                        address = " | " + geoCode.title
                        true
                    } else
                        false
                },
                point
        )
        mc.overlayManager.myLocation.addMyLocationListener { myLocation ->
            val result = FloatArray(3)
            Location.distanceBetween(
                    place.latitude!!,
                    place.longitude!!,
                    myLocation.geoPoint.lat,
                    myLocation.geoPoint.lon,
                    result
            )
            viewState.updatePosition((result[0] / 1000).toString().substring(0, 3) + " км" + address)
        }
    }

    fun startLoadingPlace(id: Int) {
        viewState.startLoading()
        placeRepository.getPlaceById(id)
                .subscribe({ place ->
                    this@PlacePresenter.place = place
                    if (allowTracking)
                        startPositionTracking(map)
                    viewState.finishLoading()
                    viewState.successLoading(place)
                }, { e ->
                    viewState.finishLoading()
                    when (e) {
                        is HttpException -> viewState.failedLoading(R.string.login_or_password_not_match)
                        is IOException -> viewState.failedLoading(R.string.network_error)
                        else -> {
                            viewState.failedLoading(R.string.wft)
                            com.pawegio.kandroid.e("PlaceListLoading", e.message!!)
                        }
                    }
                })
    }

    init {
        App.appComponent.inject(this)
    }
}