package ru.rougsig.ambercard.common.json

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson
import io.realm.RealmList
import ru.rougsig.ambercard.features.place.data.CategoryModel
import ru.rougsig.ambercard.features.place.data.CategoryRepository

/**
 * Created by rougs on 17-Oct-17.
 */
@Retention(AnnotationRetention.RUNTIME)
@JsonQualifier
annotation class CategoryFromId

class CategoryFromIdAdapter {
    @ToJson
    fun toJson(@CategoryFromId categories: RealmList<CategoryModel>): List<Int> = categories.map { it.id }

    @FromJson
    @CategoryFromId
    fun fromJson(ids: List<Int>): RealmList<CategoryModel> = RealmList<CategoryModel>().apply {
        ids.forEach {
            this.add(CategoryRepository.getCategoryById(it))
        }
    }
}