package ru.rougsig.ambercard.features.place.data

import io.realm.Realm
import mu.KotlinLogging
import ru.rougsig.ambercard.common.API.REST.Companion.api
import ru.rougsig.ambercard.common.CommonRepository
import ru.rougsig.ambercard.utils.enqueue

/**
 * Created by rougs on 11-Oct-17.
 */
object PlaceRepository {
    private val logger = KotlinLogging.logger("PlaceRepository")

    fun getPlaceById(
            id: Int,
            onSuccess: (place: PlaceModel) -> Unit,
            onFailure: () -> Unit
    ) {
        var place: PlaceModel? = null
        Realm.getDefaultInstance().executeTransaction { realm ->
            place = realm.where(PlaceModel::class.java).equalTo("id", id).findFirst()
        }
        if (place == null) {
            api.getPlaceById(id).enqueue(
                    { _, response ->
                        when (response.code()) {
                            200 -> {
                                val place = response.body()!!
                                Realm.getDefaultInstance().executeTransaction { realm ->
                                    realm.copyToRealmOrUpdate(place)
                                }
                                onSuccess(place)
                            }
                            else -> onFailure()
                        }
                    },
                    { _, t ->
                        logger.error("getPlaceById", t)
                        onFailure()
                    }
            )
        } else
            onSuccess(place!!)
    }

    private fun getAllPlaces(): List<PlaceModel> = Realm.getDefaultInstance().where(PlaceModel::class.java).findAll()!!

    fun getAllPlaces(
            onSuccess: (places: List<PlaceModel>) -> Unit,
            onFailure: () -> Unit,
            forceUpdate: Boolean = false
    ) {
        val list: List<PlaceModel> = getAllPlaces()
        if (list.isEmpty() || forceUpdate) {
            CommonRepository.getContent(
                    { onSuccess(getAllPlaces()) },
                    { onFailure() }
            )
        } else
            onSuccess(list)
    }

    fun getPlacesByFilter(filter: Array<Int>): List<PlaceModel> {
        return Realm.getDefaultInstance().where(PlaceModel::class.java).`in`("category.id", filter).findAll()
    }
}