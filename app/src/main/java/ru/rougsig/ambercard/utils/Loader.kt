package ru.rougsig.ambercard.utils

/**
 * Created by rougs on 11-Oct-17.
 */
interface Loader<T> {
    fun onLoaded(item: T)
}