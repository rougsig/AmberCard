package ru.rougsig.ambercard.utils

import android.app.Activity
import android.app.ActivityOptions
import android.content.Context
import android.content.Intent

/**
 * Created by rougs on 19-Oct-17.
 */

fun Context.startActivityWithAnimation(intent: Intent, enterResID: Int, exitResID: Int) {
    val options = ActivityOptions.makeCustomAnimation(this, enterResID, exitResID)
    this.startActivity(intent, options.toBundle())
}

fun Activity.startActivityForResultWithAnimation(intent: Intent, requestCode: Int, enterResID: Int, exitResID: Int) {
    val options = ActivityOptions.makeCustomAnimation(this, enterResID, exitResID)
    this.startActivityForResult(intent, requestCode, options.toBundle())
}