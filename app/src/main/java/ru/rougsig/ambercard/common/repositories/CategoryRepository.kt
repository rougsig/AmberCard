package ru.rougsig.ambercard.common.repositories

import io.reactivex.Single
import io.realm.Realm
import ru.rougsig.ambercard.common.api.ApiRoutes
import ru.rougsig.ambercard.features.place.models.CategoryModel
import ru.rougsig.ambercard.features.place.models.PlaceModel
import ru.rougsig.ambercard.utils.applySchedulers

/**
 * Created by rougs on 22-Oct-17.
 */

// TODO[API] need api/category/get/{id}
class CategoryRepository(val api: ApiRoutes) {
    fun getCategoryById(id: Int, forceUpdate: Boolean = false): Single<CategoryModel> {
        var category = getNullableCategoryById(id)
        return if (category == null) {
            category = CategoryModel().apply { this.id = id }
            Realm.getDefaultInstance().insert(category)
            Single.fromCallable { category!! }
                    .applySchedulers()
        } else
            Single.fromCallable { category!! }
                    .applySchedulers()
    }

    fun getCategoriesByFilter(filter: List<Int>): List<CategoryModel> = Realm.getDefaultInstance().where(CategoryModel::class.java).`in`("id", filter.toTypedArray()).findAll()
    // Fix for Thread
    fun getCategoriesByPlace(place: PlaceModel): List<CategoryModel> = Realm.getDefaultInstance().where(PlaceModel::class.java).equalTo("id", place.id).findFirst()!!.category
    private fun getNullableCategoryById(id: Int) = Realm.getDefaultInstance().where(CategoryModel::class.java).equalTo("id", id).findFirst()
}