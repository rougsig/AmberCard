package ru.rougsig.ambercard.common.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by rougs on 23-Oct-17.
 */
@StateStrategyType(AddToEndSingleStrategy::class)
interface PermissionView : MvpView {
    fun onPermissionGranted(requestCode: Int)
    fun onPermissionDenied(requestCode: Int)
}