package ru.rougsig.ambercard.features.user.auth.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.design.widget.TextInputEditText
import android.support.design.widget.TextInputLayout
import android.widget.Button
import android.widget.FrameLayout
import android.widget.LinearLayout
import android.widget.ProgressBar
import com.arellomobile.mvp.MvpAppCompatActivity
import com.arellomobile.mvp.presenter.InjectPresenter
import com.pawegio.kandroid.i
import com.pawegio.kandroid.visible
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.App
import ru.rougsig.ambercard.common.repositories.UserRepository
import ru.rougsig.ambercard.features.place.ui.activities.PlaceListActivity
import ru.rougsig.ambercard.features.user.auth.presenters.SignInPresenter
import ru.rougsig.ambercard.features.user.auth.views.SignInView
import ru.rougsig.ambercard.utils.bindView
import javax.inject.Inject

class SignInActivity : MvpAppCompatActivity(), SignInView {
    @InjectPresenter
    lateinit var signInPresenter: SignInPresenter

    private val root by bindView<FrameLayout>(R.id.root)
    private val content by bindView<LinearLayout>(R.id.content)

    private val login by bindView<TextInputEditText>(R.id.login)
    private val password by bindView<TextInputEditText>(R.id.password)
    private val loginLayout by bindView<TextInputLayout>(R.id.login_layout)
    private val passwordLayout by bindView<TextInputLayout>(R.id.password_layout)
    private val btnSubmit by bindView<Button>(R.id.btn_submit)
    private val progressBar by bindView<ProgressBar>(R.id.progress_bar)

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
        progressBar.visible = true
        content.visible = false
    }

    override fun finishSignIn() {
        progressBar.visible = false
        content.visible = true
    }

    override fun successSignIn() {
        startActivity(Intent(this, PlaceListActivity::class.java))
        finish()
    }

    override fun failedSignIn(error: Int) {
        Snackbar.make(root, getString(error), Snackbar.LENGTH_LONG).show()
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
