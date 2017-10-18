package ru.rougsig.ambercard.utils

import com.squareup.moshi.FromJson
import com.squareup.moshi.ToJson
import io.realm.RealmList
import ru.rougsig.ambercard.features.place.data.CategoryModel


/**
 * Created by rougs on 15-Oct-17.
 */
class RealmListMoshiJsonAdapter {
    @ToJson
    fun toJsonCategory(realmList: RealmList<CategoryModel>): Collection<CategoryModel> {
        return realmList
    }

    @FromJson
    fun fromJsonCategory(collection: Collection<CategoryModel>): RealmList<CategoryModel> {
        val realmList = RealmList<CategoryModel>()
        realmList.addAll(collection)
        return realmList
    }

    @ToJson
    fun toJsonString(realmList: RealmList<String>): Collection<String> {
        return realmList
    }

    @FromJson
    fun fromJsonString(collection: Collection<String>): RealmList<String> {
        val realmList = RealmList<String>()
        realmList.addAll(collection)
        return realmList
    }
}