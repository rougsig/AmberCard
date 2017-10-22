package ru.rougsig.ambercard.features.place.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.App
import ru.rougsig.ambercard.common.repositories.PlaceRepository
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

    init {
        App.appComponent.inject(this)
    }
}