package ru.rougsig.ambercard.common.di

import android.content.Context
import dagger.Component
import ru.rougsig.ambercard.common.di.modules.ApiModule
import ru.rougsig.ambercard.common.di.modules.ContextModule
import ru.rougsig.ambercard.common.di.modules.RetrofitModule
import ru.rougsig.ambercard.common.json.CategoryFromIdAdapter
import ru.rougsig.ambercard.features.place.presenters.PlaceListPresenter
import ru.rougsig.ambercard.features.user.auth.presenters.SignInPresenter
import ru.rougsig.ambercard.features.user.auth.ui.activities.SignInActivity
import ru.rougsig.ambercard.features.user.auth.ui.adapters.PlaceAdapter
import javax.inject.Singleton

/**
 * Created by rougs on 21-Oct-17.
 */
@Singleton
@Component(modules = arrayOf(
        ContextModule::class,
        RetrofitModule::class,
        ApiModule::class
))
interface AppComponent {
    fun getContext(): Context

    fun inject(presenter: SignInPresenter)
    fun inject(adapter: CategoryFromIdAdapter)
    fun inject(signInActivity: SignInActivity)
    fun inject(placeListPresenter: PlaceListPresenter)
    fun inject(placeAdapter: PlaceAdapter)
}