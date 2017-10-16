package ru.rougsig.ambercard.features.user

import android.databinding.ObservableBoolean
import android.view.View
import android.widget.EditText
import android.widget.Toast
import com.basgeekball.awesomevalidation.AwesomeValidation
import com.basgeekball.awesomevalidation.ValidationStyle
import com.basgeekball.awesomevalidation.utility.RegexTemplate
import com.stfalcon.androidmvvmhelper.mvvm.activities.ActivityViewModel
import ru.rougsig.ambercard.App.Companion.context
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.features.user.data.UserModel
import ru.rougsig.ambercard.features.user.data.UserRepository

/**
 * Created by rougs on 16-Oct-17.
 */

class LoginActivityVM(activity: LoginActivity) : ActivityViewModel<LoginActivity>(activity) {
    private val validation = AwesomeValidation(ValidationStyle.BASIC)
    private lateinit var login: EditText
    private lateinit var pass: EditText

    val inLoading = ObservableBoolean(false)

    override fun onResume() {
        super.onResume()
        login = activity.findViewById(R.id.login)
        pass = activity.findViewById(R.id.pass)

        validation.addValidation(login, RegexTemplate.NOT_EMPTY, activity.getString(R.string.required_field))
        validation.addValidation(pass, RegexTemplate.NOT_EMPTY, activity.getString(R.string.required_field))
    }

    fun onClickLoginBtn(view: View) {
        if (validation.validate()) {
            inLoading.set(true)
            UserRepository.getToken(
                    login.text.toString().trim(),
                    pass.text.toString().trim(),
                    this::onLoginSuccess,
                    this::onLoginFailure,
                    this::onLoginUnauthorized
            )
        }
    }

    private fun onLoginSuccess(user: UserModel) {
        inLoading.set(false)
    }

    private fun onLoginFailure() {
        inLoading.set(false)
        Toast.makeText(activity, context.getString(R.string.error_wtf), Toast.LENGTH_SHORT).show()
    }

    private fun onLoginUnauthorized() {
        inLoading.set(false)
        pass.error = context.getString(R.string.error_pass)
        Toast.makeText(activity, context.getString(R.string.error_pass), Toast.LENGTH_SHORT).show()
    }
}