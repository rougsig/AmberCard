package ru.rougsig.ambercard.features.user.auth.views

import com.arellomobile.mvp.MvpView
import com.arellomobile.mvp.viewstate.strategy.AddToEndSingleStrategy
import com.arellomobile.mvp.viewstate.strategy.SkipStrategy
import com.arellomobile.mvp.viewstate.strategy.StateStrategyType

/**
 * Created by rougs on 21-Oct-17.
 */

@StateStrategyType(AddToEndSingleStrategy::class)
interface SignInView : MvpView {
    fun startSignIn()
    fun finishSignIn()

    @StateStrategyType(SkipStrategy::class)
    fun successSignIn()

    fun failedSignIn(error: Int)

    fun showFormError(loginError: Int?, passwordError: Int?)
    fun hideFormError()
}