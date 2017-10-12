package ru.rougsig.ambercard.utils

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import ru.rougsig.ambercard.common.models.Place

/**
 * Created by rougs on 11-Oct-17.
 */
class JsonParser private constructor() {
    private object Holder {
        val parser = Moshi.Builder()
                .add(KotlinJsonAdapterFactory())
                .build()!!
    }

    companion object {
        val parser: Moshi  by lazy { Holder.parser }
    }
}