package ru.rougsig.ambercard.features.place.ui.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.widget.LinearLayoutManager
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.pawegio.kandroid.visible
import kotlinx.android.synthetic.main.activity_place_list.*
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.App
import ru.rougsig.ambercard.common.repositories.PlaceRepository
import ru.rougsig.ambercard.features.place.models.CategoryModel
import ru.rougsig.ambercard.features.place.models.PlaceModel
import ru.rougsig.ambercard.features.place.presenters.PlaceListPresenter
import ru.rougsig.ambercard.features.place.ui.adapters.PlaceAdapter
import ru.rougsig.ambercard.features.place.ui.views.FilterDialog
import ru.rougsig.ambercard.features.place.views.PlaceListView
import javax.inject.Inject

class PlaceListActivity : MvpAppCompatActivity(), PlaceListView {
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
    }

    override fun startLoading() {
        progress_bar.visible = true
        allowRefresh = false
    }

    override fun finishLoading() {
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
        recycler.adapter = PlaceAdapter(places)
    }

    override fun failedLoading(error: Int) {
        Snackbar.make(root, getString(error), Snackbar.LENGTH_LONG).show()
    }
}