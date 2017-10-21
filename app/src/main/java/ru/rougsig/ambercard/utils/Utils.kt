package ru.rougsig.ambercard.utils

import io.reactivex.Observable
import io.reactivex.schedulers.Schedulers
import io.reactivex.android.schedulers.AndroidSchedulers;


/**
 * Created by rougs on 21-Oct-17.
 */

fun <T> Observable<T>.applySchedulers() = this.observeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread())