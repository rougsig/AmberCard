package ru.rougsig.ambercard.features.splash.ui.activities

import android.content.Intent
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.App
import ru.rougsig.ambercard.common.repositories.UserRepository
import ru.rougsig.ambercard.features.place.ui.activities.PlaceListActivity
import ru.rougsig.ambercard.features.user.auth.ui.activities.SignInActivity
import javax.inject.Inject


class SplashActivity : AppCompatActivity() {
    @Inject
    lateinit var userRepository: UserRepository

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash)

        App.appComponent.inject(this)
        if (userRepository.getNullableUser() != null) {
            startActivity(Intent(this, PlaceListActivity::class.java))
            finish()
        } else {
            startActivity(Intent(this, SignInActivity::class.java))
            finish()
        }
    }
}
