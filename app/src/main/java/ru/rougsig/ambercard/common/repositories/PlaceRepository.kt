package ru.rougsig.ambercard.common.repositories

import io.reactivex.Single
import io.realm.Realm
import ru.rougsig.ambercard.common.api.ApiRoutes
import ru.rougsig.ambercard.features.place.models.PlaceModel
import ru.rougsig.ambercard.utils.applySchedulers
import ru.rougsig.ambercard.utils.checkDateTime
import ru.rougsig.ambercard.utils.getLastUpdated
import ru.rougsig.ambercard.utils.submitLastUpdated

/**
 * Created by rougs on 22-Oct-17.
 */

// TODO[API] need api/category/get/{id}
class PlaceRepository(val api: ApiRoutes, val userRepository: UserRepository) {
    fun getPlaceById(id: Int, forceUpdate: Boolean = false): Single<PlaceModel> {
        val category = getNullableCategoryById(id)
        return if (forceUpdate || category == null)
            api.getPlaceById(id, userRepository.getToken())
                    .applySchedulers()
                    .doOnSuccess { Realm.getDefaultInstance().insert(it) }
        else
            Single.fromCallable { category!! }.applySchedulers()
    }

    fun getAllPlaces(forceUpdate: Boolean = false): Single<List<PlaceModel>> {
        return if (forceUpdate || checkDateTime(getLastUpdated()))
            api.getContent(userRepository.getToken())
                    .doOnSuccess { data ->
                        submitLastUpdated()
                        Realm.getDefaultInstance().executeTransaction { realm ->
                            realm.insertOrUpdate(data.places)
                            realm.insertOrUpdate(data.categories)
                        }
                    }
                    .map { it.places }
                    .applySchedulers()
        else
            Single.fromCallable { getAllPlacesFromDB() }
    }

    fun getPlacesByFilter(filter: Array<Int>): List<PlaceModel> = Realm.getDefaultInstance().where(PlaceModel::class.java).`in`("category.id", filter).findAll()
    fun getAllPlacesFromDB(): List<PlaceModel> = Realm.getDefaultInstance().where(PlaceModel::class.java).findAll()!!
    private fun getNullableCategoryById(id: Int) = Realm.getDefaultInstance().where(PlaceModel::class.java).equalTo("id", id).findFirst()
}