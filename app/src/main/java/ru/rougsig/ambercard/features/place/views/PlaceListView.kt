package ru.rougsig.ambercard.features.place.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType
import ru.rougsig.ambercard.features.place.models.CategoryModel
import ru.rougsig.ambercard.features.place.models.PlaceModel

/**
 * Created by rougs on 22-Oct-17.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface PlaceListView : MvpView {
    fun startLoading()
    fun finishLoading()

    fun startRefreshing()
    fun finishRefreshing()

    fun successLoading(places: List<PlaceModel>)
    fun failedLoading(error: Int)

    fun applyFilter(categories: List<CategoryModel>)
}