package ru.rougsig.ambercard.features.place.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import io.realm.Realm
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.App
import ru.rougsig.ambercard.common.repositories.PlaceRepository
import ru.rougsig.ambercard.features.place.ui.views.FilterDialog
import ru.rougsig.ambercard.features.place.views.PlaceListView
import java.io.IOException
import javax.inject.Inject

/**
 * Created by rougs on 22-Oct-17.
 */
@InjectViewState
class PlaceListPresenter() : MvpPresenter<PlaceListView>() {
    @Inject
    lateinit var placeRepository: PlaceRepository
    private lateinit var filterDialog: FilterDialog

    override fun onFirstViewAttach() {
        load()
    }

    fun load(forceUpdate: Boolean = false) {
        if (forceUpdate)
            viewState.startRefreshing()
        else
            viewState.startLoading()
        placeRepository.getAllPlaces(forceUpdate)
                .subscribe(
                        { places ->
                            viewState.finishLoading()
                            viewState.finishRefreshing()
                            viewState.successLoading(places)
                        },
                        { e ->
                            viewState.finishLoading()
                            viewState.finishRefreshing()
                            when (e) {
                                is IOException -> viewState.failedLoading(R.string.network_error)
                                else -> viewState.failedLoading(R.string.wft)
                            }
                        }
                )
    }

    fun provideFilterDialog(filterDialog: FilterDialog) {
        this.filterDialog = filterDialog
        this.filterDialog.onDismiss = this::onHideFilter
    }

    fun showFilter() {
        filterDialog.show()
    }

    private fun onHideFilter() {
        val filter = filterDialog.filter.map { it.id }
        if (!filter.isEmpty()) {
            val filteredList = placeRepository.getPlacesByFilter(filter.toTypedArray())
            filteredList.forEach { place ->
                place.category.forEachIndexed { index, category ->
                    if (filter.contains(category.id))
                        Realm.getDefaultInstance().executeTransaction {
                            place.category.move(index, 0)
                        }
                }
            }
            viewState.successLoading(filteredList)
        } else
            viewState.successLoading(placeRepository.getAllPlaces())
    }

    init {
        App.appComponent.inject(this)
    }
}