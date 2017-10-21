package ru.rougsig.ambercard.common.presenters

import com.arellomobile.mvp.MvpPresenter
import com.arellomobile.mvp.MvpView
import io.reactivex.disposables.Disposable

/**
 * Created by rougs on 21-Oct-17.
 */
open class BasePresenter<V : MvpView> : MvpPresenter<V>() {
    private val disposables = ArrayList<Disposable>()
    protected fun disposeOnDestroy(subscription: Disposable) = disposables.add(subscription)

    override fun onDestroy() {
        super.onDestroy()
        disposables.forEach {
            it.dispose()
        }
    }
}