package ru.rougsig.ambercard.features.auth.ui.activities

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.widget.Button
import android.widget.ProgressBar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.di.DaggerAppComponent
import ru.rougsig.ambercard.features.auth.presenters.SignInPresenter
import ru.rougsig.ambercard.features.auth.views.SignInView
import ru.rougsig.ambercard.utils.bindView

class SignInActivity : MvpAppCompatActivity(), SignInView {
    @InjectPresenter
    lateinit var signInPresenter: SignInPresenter

    val login by bindView<TextInputEditText>(R.id.login)
    val password by bindView<TextInputEditText>(R.id.password)
    val loginLayout by bindView<TextInputLayout>(R.id.login_layout)
    val passwordLayout by bindView<TextInputLayout>(R.id.password_layout)
    val btnSubmit by bindView<Button>(R.id.btn_submit)
    val progressBar by bindView<ProgressBar>(R.id.progress_bar)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_in)

        btnSubmit.setOnClickListener { attemptLogin() }
    }

    private fun attemptLogin() {
        signInPresenter.signIn(
                login.text.toString(),
                password.text.toString()
        )
    }

    override fun startSignIn() {
    }

    override fun finishSignIn() {
    }

    override fun successSignIn() {
    }

    override fun failedSignIn(msg: String) {
    }

    override fun showFormError(loginError: Int?, passwordError: Int?) {
        if (loginError != null)
            loginLayout.error = getString(loginError)
        if (passwordError != null)
            passwordLayout.error = getString(passwordError)
    }

    override fun hideFormError() {
        loginLayout.error = null
        passwordLayout.error = null
    }
}
