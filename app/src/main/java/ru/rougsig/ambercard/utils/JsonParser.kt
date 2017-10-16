package ru.rougsig.ambercard.utils

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import ru.rougsig.ambercard.common.models.CategoryModel
import com.squareup.moshi.Types.newParameterizedType
import io.realm.RealmList


/**
 * Created by rougs on 11-Oct-17.
 */
class JsonParser private constructor() {
    private object Holder {
        val parser = Moshi.Builder()
                .add(RealmListMoshiJsonAdapter())
                .add(KotlinJsonAdapterFactory())
                .build()!!
    }

    companion object {
        val parser: Moshi by lazy { Holder.parser }
    }
}