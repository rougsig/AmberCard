package ru.rougsig.ambercard.common

/**
 * Created by rougs on 12-Oct-17.
 */
interface Repository<out T> {
    fun get(loaded: (T) -> Unit)
}