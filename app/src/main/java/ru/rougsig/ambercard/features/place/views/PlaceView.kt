package ru.rougsig.ambercard.features.place.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.rougsig.ambercard.custom.yandexMap.CustomMapView
import ru.rougsig.ambercard.features.place.models.PlaceModel

/**
 * Created by rougs on 23-Oct-17.
 */
@StateStrategyType(SkipStrategy::class)
interface PlaceView : MvpView {
    fun updatePosition(position: String)

    fun startLoading()
    fun finishLoading()

    fun successLoading(place: PlaceModel)
    fun failedLoading(error: Int)
}