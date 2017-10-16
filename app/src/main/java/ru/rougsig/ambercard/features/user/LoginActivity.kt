package ru.rougsig.ambercard.features.user

import com.stfalcon.androidmvvmhelper.mvvm.activities.BindingActivity
import ru.rougsig.ambercard.BR
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.databinding.ActivityLoginBinding


/**
 * Created by rougs on 16-Oct-17.
 */

class LoginActivity : BindingActivity<ActivityLoginBinding, LoginActivityVM>() {

    override fun onCreate(): LoginActivityVM {
        return LoginActivityVM(this)
    }

    override fun getVariable(): Int {
        return BR.viewModel
    }

    override fun getLayoutId(): Int {
        return R.layout.activity_login
    }
}