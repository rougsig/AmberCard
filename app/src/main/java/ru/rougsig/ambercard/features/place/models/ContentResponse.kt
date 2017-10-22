package ru.rougsig.ambercard.features.place.models

import com.squareup.moshi.Json

/**
 * Created by rougs on 22-Oct-17.
 */
data class ContentResponse(
        val categories: List<CategoryModel>,
        @Json(name = "points")
        val places: List<PlaceModel>
)