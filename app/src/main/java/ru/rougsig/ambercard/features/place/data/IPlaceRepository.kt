package ru.rougsig.ambercard.features.place.data

import ru.rougsig.ambercard.common.models.Place
import ru.rougsig.ambercard.utils.Loader

/**
 * Created by rougs on 11-Oct-17.
 */
interface IPlaceRepository {
    fun getPlace(loader: Loader<Place>)
}