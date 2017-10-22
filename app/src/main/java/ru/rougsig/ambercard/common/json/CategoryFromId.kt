package ru.rougsig.ambercard.common.json

import com.squareup.moshi.FromJson
import com.squareup.moshi.JsonQualifier
import com.squareup.moshi.ToJson
import io.realm.Realm
import io.realm.RealmList
import ru.rougsig.ambercard.features.place.models.CategoryModel

/**
 * Created by rougs on 22-Oct-17.
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
        ids.forEach { id ->
            var category = Realm.getDefaultInstance().where(CategoryModel::class.java).equalTo("id", id).findFirst()
            if (category == null) {
                category = CategoryModel().apply { this.id = id }
                Realm.getDefaultInstance().executeTransaction { it.insert(category!!) }
                add(category)
            } else {
                add(category)
            }
        }
    }
}