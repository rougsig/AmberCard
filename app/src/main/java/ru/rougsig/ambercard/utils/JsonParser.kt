package ru.rougsig.ambercard.utils

import com.github.salomonbrys.kotson.registerTypeAdapter
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import ru.rougsig.ambercard.common.models.Category
import ru.rougsig.ambercard.common.models.Place

/**
 * Created by rougs on 11-Oct-17.
 */
class JsonParser private constructor() {
    private object Holder {
        val INSTANCE = GsonBuilder()
                .registerTypeAdapter(Category.serializer)
                .registerTypeAdapter(Category.deserializer)
                .registerTypeAdapter(Place.serializer)
                .registerTypeAdapter(Place.deserializer)
                .create()!!
    }

    companion object {
        val gson: Gson by lazy { Holder.INSTANCE }
    }
}