package ru.rougsig.ambercard.utils

import ru.rougsig.ambercard.App

/**
 * Created by rougs on 12-Oct-17.
 */
object ResourceUtils {
    fun getString(res: Int) = App.context.getString(res)!!
}