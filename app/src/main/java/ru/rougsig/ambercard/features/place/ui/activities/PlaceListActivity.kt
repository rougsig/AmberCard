package ru.rougsig.ambercard.features.place.ui.activities

import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.AppCompatButton
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.widget.FrameLayout
import android.widget.ProgressBar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.pawegio.kandroid.visible
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.App
import ru.rougsig.ambercard.common.repositories.PlaceRepository
import ru.rougsig.ambercard.features.place.models.CategoryModel
import ru.rougsig.ambercard.features.place.models.PlaceModel
import ru.rougsig.ambercard.features.place.presenters.PlaceListPresenter
import ru.rougsig.ambercard.features.place.ui.adapters.PlaceAdapter
import ru.rougsig.ambercard.features.place.ui.views.FilterDialog
import ru.rougsig.ambercard.features.place.views.PlaceListView
import ru.rougsig.ambercard.utils.bindView
import javax.inject.Inject

class PlaceListActivity : MvpAppCompatActivity(), PlaceListView {
    @InjectPresenter
    lateinit var placeListPresenter: PlaceListPresenter
    @Inject
    lateinit var placeRepository: PlaceRepository

    private val root by bindView<FrameLayout>(R.id.root)
    private val refresh by bindView<SwipeRefreshLayout>(R.id.refresh)

    private val recycler by bindView<RecyclerView>(R.id.recycler)
    private val progressBar by bindView<ProgressBar>(R.id.progress_bar)

    private val btnFilter by bindView<AppCompatButton>(R.id.btn_filter)
    private lateinit var filterDialog: FilterDialog

    private var allowRefresh = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_place_list)
        recycler.layoutManager = LinearLayoutManager(this)
        refresh.setOnRefreshListener {
            if (allowRefresh) {
                placeListPresenter.load(true)
            } else
                refresh.isRefreshing = false
        }
        filterDialog = FilterDialog(this, this::applyFilter)
        btnFilter.setOnClickListener {
            filterDialog.show()
        }
    }

    override fun startLoading() {
        progressBar.visible = true
        allowRefresh = false
    }

    override fun finishLoading() {
        progressBar.visible = false
        allowRefresh = true
    }

    override fun startRefreshing() {
        refresh.isRefreshing = true
    }

    override fun finishRefreshing() {
        refresh.isRefreshing = false
    }

    override fun successLoading(places: List<PlaceModel>) {
        if (places.isNotEmpty()) {
            if (!filterDialog.filter.isEmpty())
                applyFilter(filterDialog.filter)
            else
                recycler.adapter = PlaceAdapter(places, filterDialog.filter.map { it.id }.toTypedArray())
        } else
            Snackbar.make(root, getString(R.string.empty_list), Snackbar.LENGTH_LONG).show()
    }

    override fun failedLoading(error: Int) {
        Snackbar.make(root, getString(error), Snackbar.LENGTH_LONG).show()
    }

    override fun applyFilter(categories: List<CategoryModel>) {
        val filteredPlaces: List<PlaceModel> = if (categories.isEmpty())
            placeRepository.getAllPlaces()
        else
            placeRepository.getPlacesByFilter(categories.map { it.id }.toTypedArray())
        recycler.adapter = PlaceAdapter(filteredPlaces, filterDialog.filter.map { it.id }.toTypedArray())
    }

    init {
        App.appComponent.inject(this)
    }
}