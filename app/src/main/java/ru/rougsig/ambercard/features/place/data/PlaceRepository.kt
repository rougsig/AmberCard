package ru.rougsig.ambercard.features.place.data

import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.models.Place
import ru.rougsig.ambercard.helpers.RawJson
import ru.rougsig.ambercard.utils.JsonParser
import ru.rougsig.ambercard.utils.Loader

/**
 * Created by rougs on 11-Oct-17.
 */
object PlaceRepository: IPlaceRepository {
    override fun getPlace(loader: Loader<Place>) {
        loader.onLoaded(JsonParser.gson.fromJson(RawJson.ab_two, Place::class.java))
    }
}