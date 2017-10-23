package ru.rougsig.ambercard.common.presenters

import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import com.arellomobile.mvp.InjectViewState
import com.arellomobile.mvp.MvpPresenter
import ru.rougsig.ambercard.common.views.PermissionView

/**
 * Created by rougs on 23-Oct-17.
 */
@InjectViewState
class PermissionPresenter : MvpPresenter<PermissionView>() {
    fun requestPermission(activity: Activity, permission: String, requestCode: Int) {
        val granted = ActivityCompat.checkSelfPermission(activity, permission) == PackageManager.PERMISSION_GRANTED
        if (granted)
            viewState.onPermissionGranted(requestCode)
        else
            ActivityCompat.requestPermissions(activity, arrayOf(permission), requestCode)
    }

    fun checkGrantedPermission(grantResults: IntArray, requestCode: Int) {
        if (grantResults.none { it != PackageManager.PERMISSION_GRANTED })
            viewState.onPermissionGranted(requestCode)
        else
            viewState.onPermissionDenied(requestCode)
    }
}