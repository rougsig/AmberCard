package ru.rougsig.ambercard.features.place.data

import io.realm.Realm

/**
 * Created by rougs on 17-Oct-17.
 */
object CategoryRepository {
    fun getCategoryById(id: Int): CategoryModel {
        var category: CategoryModel? = Realm.getDefaultInstance().where(CategoryModel::class.java).equalTo("id", id).findFirst()
        return if (category == null) {
            Realm.getDefaultInstance().executeTransaction { realm ->
                category = CategoryModel().apply { this.id = id }
                realm.insert(category!!)
            }
            category!!
        } else
            category!!
    }

    fun getAllCategory(): List<CategoryModel> = Realm.getDefaultInstance().where(CategoryModel::class.java).findAll()!!
}