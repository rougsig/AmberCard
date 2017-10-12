package ru.rougsig.ambercard.features.place.data

import ru.rougsig.ambercard.common.models.Place
import ru.rougsig.ambercard.common.Repository
import ru.rougsig.ambercard.helpers.RawJson
import ru.rougsig.ambercard.utils.JsonParser

/**
 * Created by rougs on 11-Oct-17.
 */
object PlaceRepository : Repository<Place> {
    override fun get(loaded: (Place) -> Unit) = loaded(
            JsonParser.parser.adapter<Place>(Place::class.java).fromJson(RawJson.ab_one)!!
    )
}