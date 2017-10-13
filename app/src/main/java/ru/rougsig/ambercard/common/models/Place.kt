package ru.rougsig.ambercard.common.models

import android.text.SpannableString
import com.squareup.moshi.FromJson
import com.squareup.moshi.Json
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.helpers.RawJson
import ru.rougsig.ambercard.utils.JsonParser
import ru.rougsig.ambercard.utils.TextUtils
import ru.rougsig.ambercard.utils.defaultIfEmptyOrNull

/**
 * Created by rougs on 11-Oct-17.
 */
data class Place(
        val id: Int,
        var name: String,
        var description: String,
        @Json(name = "category_id") var category: List<Category>,
        var photos: List<String>,

        @Json(name = "description_2") var workTime: String?,
        var latitude: Float? = null,
        var longitude: Float? = null,
        var rate: Int?,
        @Json(name = "cost_text") var costText: String?,
        @Json(name = "cost_sum") var costValue: String?,
        var phone: String?,
        var site: String?,
        @Json(name = "discount_min") var discountMin: Int?,
        @Json(name = "discount_max") var discountMax: Int?,
        @Json(name = "discount_conditions") var discountCondition: String?,
        @Json(name = "min_people") var peopleMin: Int?,
        @Json(name = "max_people") var peopleMax: Int?
) {
    fun getCost(): SpannableString = TextUtils.getTitleText(costText, costValue)
    fun getWorkTime(): SpannableString = TextUtils.getTitleText(R.string.defaults_work_text, workTime)

    init {
        workTime = workTime.defaultIfEmptyOrNull(R.string.defaults_work_time)

        costText = costText.defaultIfEmptyOrNull(R.string.defaults_cost_text)
        costValue = costValue.defaultIfEmptyOrNull(R.string.defaults_cost_value)
    }
}