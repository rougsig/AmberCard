package ru.rougsig.ambercard.features.place.models

import com.squareup.moshi.Json
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.json.CategoryFromId
import ru.rougsig.ambercard.utils.defaultIfEmptyOrNull

/**
 * Created by rougs on 22-Oct-17.
 */
open class PlaceModel : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    @Required
    var name: String = ""
    @Required
    var description: String = ""
    @CategoryFromId
    @Json(name = "category_id")
    @Required
    var category: RealmList<CategoryModel> = RealmList()
    @Required
    var photos: RealmList<String> = RealmList()
    @Required
    @Json(name = "description_2")
    var time: String = ""
        get() = field.defaultIfEmptyOrNull(R.string.defaults_work_time)
    var latitude: Double = 0.0
    var longitude: Double = 0.0
    var rate: Int = 0
    @Json(name = "cost_text")
    var costText: String = ""
        get() = field.defaultIfEmptyOrNull(R.string.defaults_cost_text)
    @Json(name = "cost_sum")
    var costValue: String = ""
        get() = field.defaultIfEmptyOrNull(R.string.defaults_cost_value)
    var phone: String? = null
    var site: String? = null
    @Json(name = "discount_min")
    var discountMin: Int = 0
    @Json(name = "discount_max")
    var discountMax: Int = 0
    @Json(name = "discount_conditions")
    var discountCondition: String? = null
    @Json(name = "min_people")
    var peopleMin: Int? = 0
    @Json(name = "max_people")
    var peopleMax: Int? = 0
}