package ru.rougsig.ambercard.common.models

import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.jsonDeserializer
import com.github.salomonbrys.kotson.jsonSerializer
import com.github.salomonbrys.kotson.nullInt
import com.google.gson.JsonObject
import ru.rougsig.ambercard.utils.JsonParser
import ru.rougsig.ambercard.utils.TextViewUtils
import java.util.concurrent.locks.Condition

/**
 * Created by rougs on 11-Oct-17.
 */
data class Place(
        val id: Int,
        val name: String,
        val category: Array<Category>,
        val description: String,
        val descriptionTwo: String,
        val latitude: Float,
        val longitude: Float,
        val rate: Int,
        val costSum: String,
        val costText: String,
        val phone: String,
        val site: String,
        val discountMin: Int?,
        val discountMax: Int?,
        val discountCondition: String,
        val peopleMin: Int?,
        val peopleMax: Int?,
        val photos: Array<String>
) {
    companion object {
        val serializer = jsonSerializer<Place> {
            JsonObject()
        }
        val deserializer = jsonDeserializer<Place> {
            val json = it.json.asJsonObject
            Place(
                    id = json["id"].asInt,
                    name = json["name"].asString,
                    category = JsonParser.gson.fromJson(json["category_id"], Array<Category>::class.java),
                    description = json["description"].asString,
                    descriptionTwo = json["description_2"].asString,
                    latitude = json["latitude"].asFloat,
                    longitude = json["longitude"].asFloat,
                    rate = json["rate"].asInt,
                    costSum = json["cost_sum"].asString,
                    costText = json["cost_text"].asString,
                    phone = json["phone"].asString,
                    site = json["site"].asString,
                    discountMin = json["discount_min"].asInt,
                    discountMax = json["discount_max"].asInt,
                    discountCondition = json["discount_conditions"].asString,
                    peopleMin = if (json.has("min_people")) json["min_people"].let { if (it.isJsonNull) null else it.asInt } else null,
                    peopleMax = if (json.has("max_people")) json["max_people"].let { if (it.isJsonNull) null else it.asInt } else null,
                    photos = JsonParser.gson.fromJson(it.json["photos"], Array<String>::class.java)
            )
        }
    }
}