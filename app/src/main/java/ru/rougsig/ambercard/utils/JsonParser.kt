package ru.rougsig.ambercard.utils

import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.moshi.Types.newParameterizedType
import io.realm.RealmList
import ru.rougsig.ambercard.common.models.CategoryModel


/**
 * Created by rougs on 11-Oct-17.
 */
class JsonParser private constructor() {
    private object Holder {
        val parser = Moshi.Builder()
                .add(RealmListMoshiJsonAdapter())
                .add(KotlinJsonAdapterFactory())
                .build()!!.apply {
            adapter<Map<String, String>>(Types.newParameterizedType(Map::class.java, String::class.java, String::class.java))
        }
    }

    companion object {
        val parser: Moshi by lazy { Holder.parser }
    }
}