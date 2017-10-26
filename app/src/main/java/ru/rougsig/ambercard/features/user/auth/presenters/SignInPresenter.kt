package ru.rougsig.ambercard.features.user.auth.presenters

import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import retrofit2.HttpException
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.App
import ru.rougsig.ambercard.common.repositories.UserRepository
import ru.rougsig.ambercard.features.user.auth.views.SignInView
import java.io.IOException
import javax.inject.Inject

/**
 * Created by rougs on 21-Oct-17.
 */
@InjectViewState
class SignInPresenter() : MvpPresenter<SignInView>() {
    @Inject
    lateinit var userRepository: UserRepository

    fun signIn(login: String, password: String) {
        // Валидацию лучше вынести в отдельную функцию, а ещё лучше класс или наор классов-валидаторов.
        var loginError: Int? = null
        var passwordError: Int? = null

        viewState.hideFormError()

        // Однострочные читабельней
        // Если заполню пробелами - прокатит. Используй isBlank()
        if (login.isEmpty())
            loginError = R.string.empty_login
        if (password.isEmpty())
            passwordError = R.string.empty_password
        if (loginError != null || passwordError != null) {
            viewState.showFormError(loginError, passwordError)
            return
        }

        viewState.startSignIn()
        userRepository.signIn(login, password)
                .subscribe(
                        // Убери вообще -> ты не используешь, а если надо, то лучше it
                        { token ->
                            viewState.successSignIn()
                            viewState.finishSignIn()
                        },
                        { e ->
                            viewState.finishSignIn()
                            when (e) {
                                // Лучше сделать хэндлер ошибок
                                is HttpException -> viewState.failedSignIn(R.string.login_or_password_not_match)
                                is IOException -> viewState.failedSignIn(R.string.network_error)
                                else -> {
                                    viewState.failedSignIn(R.string.wft)
                                }
                            }
                        }
                )
    }

    init {
        App.appComponent.inject(this)
    }
}