package ru.rougsig.ambercard.features.place.data

import android.text.SpannableString
import com.squareup.moshi.Json
import io.realm.RealmList
import io.realm.RealmObject
import io.realm.annotations.PrimaryKey
import io.realm.annotations.Required
import ru.rougsig.ambercard.R
import ru.rougsig.ambercard.common.json.CategoryFromId
import ru.rougsig.ambercard.utils.ResourceUtils.getString
import ru.rougsig.ambercard.utils.TextUtils
import ru.rougsig.ambercard.utils.defaultIfEmptyOrNull

/**
 * Created by rougs on 11-Oct-17.
 */
open class PlaceModel constructor() : RealmObject() {
    @PrimaryKey
    var id: Int = 0
        private set

    @Required
    lateinit var name: String
        private set

    @Required
    lateinit var description: String
        private set

    @CategoryFromId
    @Json(name = "category_id")
    @Required
    var category: RealmList<CategoryModel> = RealmList()
        private set

    @Required
    var photos: RealmList<String> = RealmList()
        private set

    @Required
    @Json(name = "description_2")
    var time: String = getString(R.string.defaults_work_time)
        get() = field.defaultIfEmptyOrNull(R.string.defaults_work_time)
        private set

    var latitude: Float? = null
        private set

    var longitude: Float? = null
        private set

    var rate: Int = 0
        private set

    @Json(name = "cost_text")
    var costText: String = getString(R.string.defaults_cost_text)
        get() = field.defaultIfEmptyOrNull(R.string.defaults_cost_text)
        private set

    @Json(name = "cost_sum")
    var costValue: String = getString(R.string.defaults_cost_value)
        get() = field.defaultIfEmptyOrNull(R.string.defaults_cost_value)
        private set

    var phone: String? = null
        private set

    var site: String? = null
        private set

    @Json(name = "discount_min")
    var discountMin: Int = 0
        private set

    @Json(name = "discount_max")
    var discountMax: Int = 0
        private set

    @Json(name = "discount_conditions")
    var discountCondition: String? = null
        private set

    @Json(name = "min_people")
    var peopleMin: Int? = 0
        private set

    @Json(name = "max_people")
    var peopleMax: Int? = 0
        private set

    fun getCost(): SpannableString = TextUtils.getTitleText(costText, costValue)
    fun getWorkTime(): SpannableString = TextUtils.getTitleText(R.string.defaults_work_text, time)
    fun getPreView(): String = photos.first()!!
    fun getCategoryIcon(): String = category.first()!!.icon
    fun getDiscount(): String = "-$discountMax%"

    constructor(
            id: Int,
            name: String,
            description: String,
            category: Collection<CategoryModel>,
            photos: Collection<String>,
            time: String
    ) : this() {
        this.id = id
        this.name = name
        this.description = description
        this.category.clear()
        this.category.addAll(category)
        this.photos.clear()
        this.photos.addAll(photos)
        this.time = time
    }
}