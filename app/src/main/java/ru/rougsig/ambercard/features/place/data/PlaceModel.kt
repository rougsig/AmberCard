package ru.rougsig.ambercard.features.place.data

import android.text.SpannableString
import com.squareup.moshi.Json
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.models.CategoryModel
import ru.rougsig.ambercard.utils.TextUtils
import ru.rougsig.ambercard.utils.defaultIfEmptyOrNull

/**
 * Created by rougs on 11-Oct-17.
 */
open class PlaceModel() : RealmObject() {
    @PrimaryKey
    var id: Int = 0
    @Required
    var name: String? = null
    @Required
    var description: String? = null
    @Json(name = "category_id")
    @Required
    var category: RealmList<CategoryModel> = RealmList()
    var photos: RealmList<String> = RealmList()
    @Json(name = "description_2")
    var workTime: String? = null
    var latitude: Float? = null
    var longitude: Float? = null
    var rate: Int? = null
    @Json(name = "cost_text")
    var costText: String = ""
    @Json(name = "cost_sum")
    var costValue: String = ""
    var phone: String? = null
    var site: String? = null
    @Json(name = "discount_min")
    var discountMin: Int? = null
    @Json(name = "discount_max")
    var discountMax: Int? = null
    @Json(name = "discount_conditions")
    var discountCondition: String? = null
    @Json(name = "min_people")
    var peopleMin: Int? = null
    @Json(name = "max_people")
    var peopleMax: Int? = null

    fun getCost(): SpannableString = TextUtils.getTitleText(costText, costValue)
    fun getWorkTime(): SpannableString = TextUtils.getTitleText(R.string.defaults_work_text, workTime)
    fun getPreView(): String = photos.first()!!
    fun getCategoryIcon(): String = category.first()!!.icon!!

    init {
        workTime = workTime.defaultIfEmptyOrNull(R.string.defaults_work_time)

        costText = costText.defaultIfEmptyOrNull(R.string.defaults_cost_text)
        costValue = costValue.defaultIfEmptyOrNull(R.string.defaults_cost_value)
    }
}