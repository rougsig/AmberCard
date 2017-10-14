package ru.rougsig.ambercard.features.place.data

import ru.rougsig.ambercard.common.Repository
import ru.rougsig.ambercard.helpers.RawJson
import ru.rougsig.ambercard.utils.JsonParser

/**
 * Created by rougs on 11-Oct-17.
 */
object PlaceRepository : Repository<PlaceModel> {
    override fun get(loaded: (PlaceModel) -> Unit) {
        loaded(
                JsonParser.parser.adapter<PlaceModel>(PlaceModel::class.java).fromJson(RawJson.ab_two)!!
        )
    }
}