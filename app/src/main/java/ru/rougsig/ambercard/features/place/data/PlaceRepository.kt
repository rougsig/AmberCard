package ru.rougsig.ambercard.features.place.data

import io.realm.Realm
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import ru.rougsig.ambercard.common.API.REST.Companion.api
import ru.rougsig.ambercard.features.user.data.UserModel
import ru.rougsig.ambercard.features.user.data.UserRepository

/**
 * Created by rougs on 11-Oct-17.
 */
object PlaceRepository {
    fun getPlaceById(
            id: Int,
            onSuccess: (place: PlaceModel) -> Unit,
            onFailure: () -> Unit,
            onUnauthorized: () -> Unit) {
        var place: PlaceModel? = null
        Realm.getDefaultInstance().executeTransaction { realm ->
            place = realm.where(PlaceModel::class.java).equalTo("id", id).findFirst()
        }
        if (place == null) {
            api.getPlaceById(id).enqueue(object : Callback<PlaceModel> {
                override fun onResponse(call: Call<PlaceModel>, response: Response<PlaceModel>) {
                    when (response.code()) {
                        200 -> {
                            val place = response.body()!!
                            Realm.getDefaultInstance().executeTransaction { realm ->
                                realm.copyToRealmOrUpdate(place)
                            }
                            onSuccess(place)
                        }
                        401 -> onUnauthorized()
                    }
                }

                override fun onFailure(call: Call<PlaceModel>, t: Throwable?) {
                    onFailure()
                }
            })
        } else
            onSuccess(place!!)
    }
}