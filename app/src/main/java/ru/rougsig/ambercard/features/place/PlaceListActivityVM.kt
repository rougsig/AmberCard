package ru.rougsig.ambercard.features.place

import android.app.Activity
import android.content.Intent
import android.databinding.ObservableBoolean
import android.support.v4.widget.SwipeRefreshLayout
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.Toast
import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel
import ru.rougsig.ambercard.App.Companion.context
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.features.place.data.PlaceModel
import ru.rougsig.ambercard.features.place.data.PlaceRepository
import ru.rougsig.ambercard.features.place.helpers.PlaceAdapter
import kotlin.collections.ArrayList

/**
 * Created by rougs on 16-Oct-17.
 */


// TODO Исправить косяк, когда нету инета, сделать CallBack на тост
class PlaceListActivityVM(listActivity: PlaceListActivity) : ActivityViewModel<PlaceListActivity>(listActivity) {
    private val recycler: RecyclerView = activity.binding.recycler
    private val refresh: SwipeRefreshLayout = activity.binding.refresh
    val inLoading = ObservableBoolean(true)
    var filter: ArrayList<Int>? = null

    private var init = false

    companion object {
        val FILTER_CODE = 1
        val FILTER_DATA = "filterData"
    }

    override fun onStart() {
        if (!init) {
            refresh.setOnRefreshListener {
                load(true)
            }
            recycler.layoutManager = LinearLayoutManager(activity)
            load()
            init = true
        }
    }

    fun onClickFilter(view: View) {
        val intent = Intent(activity, PlaceFilterActivity::class.java)
        if (filter != null)
            intent.putIntegerArrayListExtra(FILTER_DATA, filter)
        activity.startActivityForResult(intent, FILTER_CODE)
    }

    private fun load(forceUpdate: Boolean = false) {
        PlaceRepository.getAllPlaces(this::onLoadedSuccess, this::onLoadedFailure, forceUpdate)
    }

    private fun onLoadedSuccess(places: List<PlaceModel>) {
        recycler.adapter = PlaceAdapter(
                places,
                this::onClickPlace
        )
        println(places.size)
        recycler.adapter.notifyDataSetChanged()
        refresh.isRefreshing = false
        inLoading.set(false)
    }

    private fun onClickPlace(place: PlaceModel) {
        val intent = Intent(activity, PlaceActivity::class.java)
        intent.putExtra(PlaceActivity.EXTRA_ID, place.id)
        activity.startActivity(intent)
    }

    private fun onLoadedFailure() {
        inLoading.set(false)
        refresh.isRefreshing = false
        Toast.makeText(activity, context.getString(R.string.error_wtf), Toast.LENGTH_SHORT).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == FILTER_CODE && resultCode == Activity.RESULT_OK) {
            val filter = data.extras.getIntegerArrayList(FILTER_DATA)
            recycler.adapter = PlaceAdapter(
                    PlaceRepository.getPlacesByFilter(filter.toTypedArray()),
                    this::onClickPlace
            )
            recycler.adapter.notifyDataSetChanged()
        }
    }
}