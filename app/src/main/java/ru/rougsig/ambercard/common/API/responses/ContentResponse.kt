package ru.rougsig.ambercard.common.API.responses

import com.squareup.moshi.Json
import ru.rougsig.ambercard.features.place.data.CategoryModel
import ru.rougsig.ambercard.features.place.data.PlaceModel

/**
 * Created by rougs on 17-Oct-17.
 */
data class ContentResponse(
        val categories: List<CategoryModel>,
        @Json(name = "points")
        val places: List<PlaceModel>
)