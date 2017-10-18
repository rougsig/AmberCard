package ru.rougsig.ambercard.features.place.data

import io.realm.Realm

/**
 * Created by rougs on 17-Oct-17.
 */
object CategoryRepository {
    fun getCategoryById(id: Int): CategoryModel {
        var category: CategoryModel? = getNullableCategoryById(id)
        return if (category == null) {
            category = CategoryModel().apply { this.id = id }
            Realm.getDefaultInstance().executeTransaction { it.insert(category!!) }
            category
        } else
            category
    }

    private fun getNullableCategoryById(id: Int): CategoryModel? = Realm.getDefaultInstance().where(CategoryModel::class.java).equalTo("id", id).findFirst()

    fun getAllCategory(): List<CategoryModel> = Realm.getDefaultInstance().where(CategoryModel::class.java).findAll()!!
}