package ru.rougsig.ambercard.features.place.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import retrofit2.HttpException
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.App
import ru.rougsig.ambercard.common.repositories.PlaceRepository
import ru.rougsig.ambercard.features.place.views.PlaceView
import java.io.IOException
import javax.inject.Inject

/**
 * Created by rougs on 23-Oct-17.
 */
@InjectViewState
class PlacePresenter : MvpPresenter<PlaceView>() {
    @Inject
    lateinit var placeRepository: PlaceRepository

    fun startLoadingPlace(id: Int) {
        viewState.startLoading()
        placeRepository.getPlaceById(id)
                .subscribe({ place ->
                    viewState.successLoading(place)
                    viewState.finishLoading()
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