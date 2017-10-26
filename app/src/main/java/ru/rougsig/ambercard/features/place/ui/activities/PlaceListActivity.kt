package ru.rougsig.ambercard.features.place.ui.activities

import android.Manifest
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.activity_place_list.*
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.di.modules.LocationModule
import ru.rougsig.ambercard.common.presenters.PermissionPresenter
import ru.rougsig.ambercard.common.views.PermissionView
import ru.rougsig.ambercard.features.place.models.PlaceModel
import ru.rougsig.ambercard.features.place.presenters.PlaceListPresenter
import ru.rougsig.ambercard.features.place.ui.adapters.PlaceAdapter
import ru.rougsig.ambercard.features.place.ui.views.FilterDialog
import ru.rougsig.ambercard.features.place.views.PlaceListView

class PlaceListActivity : MvpAppCompatActivity(), PlaceListView, PermissionView {
    @InjectPresenter
    lateinit var permissionPresenter: PermissionPresenter
    @InjectPresenter
    lateinit var placeListPresenter: PlaceListPresenter
    private var allowRefresh = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_list)
        placeListPresenter.provideFilterDialog(FilterDialog(this))
        recycler.layoutManager = LinearLayoutManager(this)
        refresh.setOnRefreshListener {
            if (allowRefresh) {
                placeListPresenter.load(true)
            } else
                refresh.isRefreshing = false
        }
        btn_filter.setOnClickListener {
            placeListPresenter.showFilter()
        }
        btn_update.setOnClickListener {
            placeListPresenter.load(true)
        }
        permissionPresenter.requestPermission(this, Manifest.permission.ACCESS_COARSE_LOCATION, PlaceActivity.ACCESS_COARSE_LOCATION_CODE)
        permissionPresenter.requestPermission(this, Manifest.permission.ACCESS_FINE_LOCATION, PlaceActivity.ACCESS_FINE_LOCATION_CODE)
    }

    override fun startLoading() {
        content.visible = false
        progress_bar.visible = true
        allowRefresh = false
    }

    override fun finishLoading() {
        content.visible = true
        progress_bar.visible = false
        allowRefresh = true
    }

    override fun startRefreshing() {
        refresh.isRefreshing = true
    }

    override fun finishRefreshing() {
        refresh.isRefreshing = false
    }

    override fun successLoading(places: List<PlaceModel>) {
        if(!places.isEmpty()) {
            recycler.adapter = PlaceAdapter(places)
            no_info.visible = false
            btn_filter.visible = true
        } else
            btn_filter.visible = false
    }

    override fun failedLoading(error: Int) {
        Snackbar.make(root, getString(error), Snackbar.LENGTH_LONG).show()
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        if (requestCode == PlaceActivity.ACCESS_COARSE_LOCATION_CODE || requestCode == PlaceActivity.ACCESS_FINE_LOCATION_CODE)
            permissionPresenter.checkGrantedPermission(grantResults, requestCode)
    }

    override fun onPermissionGranted(requestCode: Int) {
        if (requestCode == PlaceActivity.ACCESS_COARSE_LOCATION_CODE || requestCode == PlaceActivity.ACCESS_FINE_LOCATION_CODE)
            LocationModule.create(this)
    }

    override fun onPermissionDenied(requestCode: Int) {}
}