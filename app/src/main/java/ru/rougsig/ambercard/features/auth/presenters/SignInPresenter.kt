package ru.rougsig.ambercard.features.auth.presenters

import android.util.Log.i
import com.arellomobile.mvp.InjectViewState
import retrofit2.HttpException
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.App
import ru.rougsig.ambercard.common.api.ApiRoutes
import ru.rougsig.ambercard.common.presenters.BasePresenter
import ru.rougsig.ambercard.features.auth.views.SignInView
import ru.rougsig.ambercard.utils.applySchedulers
import java.io.IOException
import javax.inject.Inject

/**
 * Created by rougs on 21-Oct-17.
 */
@InjectViewState
class SignInPresenter() : BasePresenter<SignInView>() {

    @Inject
    lateinit var api: ApiRoutes

    fun signIn(login: String, password: String) {
        var loginError: Int? = null
        var passwordError: Int? = null

        viewState.hideFormError()

        if (login.isEmpty())
            loginError = R.string.empty_login
        if (password.isEmpty())
            passwordError = R.string.empty_password
        if (loginError != null || passwordError != null) {
            viewState.showFormError(loginError, passwordError)
            return
        }

        viewState.startSignIn()
        val subscription = api.signIn(login, password)
                .applySchedulers()
                .doOnNext { token ->
                    i("Token", token.token)
                }
                .subscribe(
                        {
                            viewState.finishSignIn()
                            viewState.successSignIn()
                        },
                        { e ->
                            viewState.finishSignIn()
                            if (e is HttpException)
                                viewState.failedSignIn(R.string.login_or_password_not_match)
                            if (e is IOException)
                                viewState.failedSignIn(R.string.network_error)
                        }
                )
        disposeOnDestroy(subscription)
    }

    init {
        App.appComponent.inject(this)
    }
}