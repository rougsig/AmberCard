package ru.rougsig.ambercard.common.models

import com.github.salomonbrys.kotson.get
import com.github.salomonbrys.kotson.jsonDeserializer
import com.github.salomonbrys.kotson.jsonSerializer
import com.google.gson.JsonObject
import ru.rougsig.ambercard.utils.JsonParser

/**
 * Created by rougs on 11-Oct-17.
 */
data class Category(
        val id: Int,
        val name: String,
        val icon: String,
        val picture: String
) {
    companion object {
        val serializer = jsonSerializer<Category> {
            JsonObject()
        }
        val deserializer = jsonDeserializer<Category> {
            val json = it.json.asJsonObject
            Category(
                    id = json["id"].asInt,
                    name = json["name"].asString,
                    icon = json["icon"].asString,
                    picture = json["picture"].asString
            )
        }
    }
}