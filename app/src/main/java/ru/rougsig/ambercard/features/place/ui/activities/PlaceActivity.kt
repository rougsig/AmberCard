package ru.rougsig.ambercard.features.place.ui.activities

import android.Manifest
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.widget.TextView
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.activity_place.*
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.api.baseURL
import ru.rougsig.ambercard.common.presenters.PermissionPresenter
import ru.rougsig.ambercard.common.views.PermissionView
import ru.rougsig.ambercard.features.place.models.PlaceModel
import ru.rougsig.ambercard.features.place.presenters.PlacePresenter
import ru.rougsig.ambercard.features.place.views.PlaceView
import ru.rougsig.ambercard.utils.TextUtils
import ru.rougsig.ambercard.utils.bindView

class PlaceActivity : MvpAppCompatActivity(), PermissionView, PlaceView {
    @InjectPresenter
    lateinit var permissionPresenter: PermissionPresenter
    @InjectPresenter
    lateinit var placePresenter: PlacePresenter
    companion object {
        val PLACE_ID_EXTRA = "place_id"
        val ACCESS_COARSE_LOCATION_CODE = 0
        val ACCESS_FINE_LOCATION_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place)

        placePresenter.startLoadingPlace(intent.extras.getInt(PLACE_ID_EXTRA))
        permissionPresenter.requestPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION, ACCESS_COARSE_LOCATION_CODE)
        permissionPresenter.requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, ACCESS_FINE_LOCATION_CODE)
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == ACCESS_COARSE_LOCATION_CODE || requestCode == ACCESS_FINE_LOCATION_CODE)
            permissionPresenter.checkGrantedPermission(grantResults, requestCode)
    }

    override fun onPermissionGranted(requestCode: Int) {
        placePresenter.allowMapTracking(map)
    }

    override fun onPermissionDenied(requestCode: Int) {
        distance.visible = false
        distance_line.visible = false
    }

    override fun updatePosition(position: String) {
        distance.text = position
    }

    override fun startLoading() {
        content.visible = false
        progress_bar.visible = true
    }

    override fun finishLoading() {
        content.visible = true
        progress_bar.visible = false
    }

    override fun successLoading(place: PlaceModel) {
        placePresenter.initMap(map)
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
    }

    override fun failedLoading(error: Int) {
        Snackbar.make(root, getString(error), Snackbar.LENGTH_LONG).show()
    }
}
