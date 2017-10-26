package ru.rougsig.ambercard.features.place.ui.activities

import android.Manifest
import android.annotation.SuppressLint
import android.location.Geocoder
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.activity_place.*
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.api.baseURL
import ru.rougsig.ambercard.common.custom.yandexMap.MapController
import ru.rougsig.ambercard.common.di.modules.LocationModule
import ru.rougsig.ambercard.common.presenters.PermissionPresenter
import ru.rougsig.ambercard.common.views.PermissionView
import ru.rougsig.ambercard.features.place.models.PlaceModel
import ru.rougsig.ambercard.features.place.presenters.PlacePresenter
import ru.rougsig.ambercard.features.place.ui.views.GalleryDialog
import ru.rougsig.ambercard.features.place.views.PlaceView
import ru.rougsig.ambercard.utils.TextUtils
import ru.rougsig.ambercard.utils.getDistance
import ru.yandex.yandexmapkit.overlay.Overlay
import ru.yandex.yandexmapkit.overlay.OverlayItem
import ru.yandex.yandexmapkit.utils.GeoPoint
import java.io.IOException
import java.util.*

class PlaceActivity : MvpAppCompatActivity(), PermissionView, PlaceView {
    @InjectPresenter
    lateinit var permissionPresenter: PermissionPresenter
    @InjectPresenter
    lateinit var placePresenter: PlacePresenter
    private lateinit var galleryDialog: GalleryDialog

    companion object {
        val PLACE_ID_EXTRA = "place_id"
        val ACCESS_COARSE_LOCATION_CODE = 1111
        val ACCESS_FINE_LOCATION_CODE = 2222
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

        btn_gallery.setOnClickListener { galleryDialog.show() }
        btn_close.setOnClickListener { finish() }
        placePresenter.startLoadingPlace(intent.extras.getInt(PLACE_ID_EXTRA))
        permissionPresenter.requestPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION, ACCESS_COARSE_LOCATION_CODE)
        permissionPresenter.requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_FINE_LOCATION_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == ACCESS_COARSE_LOCATION_CODE || requestCode == ACCESS_FINE_LOCATION_CODE)
            permissionPresenter.checkGrantedPermission(grantResults, requestCode)
    }

    override fun onPermissionGranted(requestCode: Int) {
        if (requestCode == PlaceActivity.ACCESS_COARSE_LOCATION_CODE || requestCode == PlaceActivity.ACCESS_FINE_LOCATION_CODE) {
            LocationModule.create(this)
        }
    }

    // Надо бы обработать эту ситуацию - показать ошибку или инфу о том что не будет работать
    override fun onPermissionDenied(requestCode: Int) {}

    override fun startLoading() {
        content.visible = false
        progress_bar.visible = true
    }

    override fun finishLoading() {
        content.visible = true
        progress_bar.visible = false
    }

    // Слишком большая функция, сложно читать, лучше раздробить на несколько типа initMap итд
    @SuppressLint("SetTextI18n")
    override fun successLoading(place: PlaceModel) {
        galleryDialog = GalleryDialog(this, place.photos.map { baseURL + it })
        img.setImageURI(baseURL + place.photos.first())
        category_img.setImageURI(baseURL + place.category.first()!!.icon)
        category.text = place.category.first()!!.name
        rating_bar.rating = place.rate
        name.text = place.name
        description.text = place.description
        work_time.text = TextUtils.getTitleText(
                getString(R.string.defaults_work_text),
                place.time,
                resources.getColor(R.color.black)
        )
        cost.text = TextUtils.getTitleText(
                place.costText,
                place.costValue,
                resources.getColor(R.color.black)
        )
        if (place.phone != null && place.phone != "")
            phone.text = place.phone

        val mc = MapController(map)
        val point = GeoPoint(
                place.latitude,
                place.longitude
        )
        val mapOverlay = Overlay(map.mapController)
        mapOverlay.addOverlayItem(
                OverlayItem(
                        point,
                        ContextCompat.getDrawable(map.context, R.drawable.ic_pin))
        )
        mc.overlayManager.addOverlay(mapOverlay)
        mc.setPositionAnimationTo(point, 14f)
        val address = try {
            Geocoder(this, Locale("ru", "RU")).getFromLocation(
                    place.latitude,
                    place.longitude,
                    1
            ).first()
        } catch (e: IOException) {
            null
        }
        val distanceText = getDistance(place.latitude, place.longitude)
        var addressText = ""
        if (!distanceText.isEmpty())
            addressText = distanceText
        if (address != null && address.subThoroughfare != "null" && address.thoroughfare != "Unnamed Road") {
            if (addressText != "")
                addressText += " | "
            addressText += "${address.thoroughfare}, ${address.subThoroughfare}"
        }
        if (addressText == "") {
            distance.visible = false
            distance_line.visible = false
        }
        distance.text = addressText
    }

    override fun failedLoading(error: Int) {
        Snackbar.make(root, getString(error), Snackbar.LENGTH_LONG).show()
    }

    override fun finish() {
        super.finish()
        overridePendingTransition(R.anim.fade_in, R.anim.slide_out_left)
    }
}
