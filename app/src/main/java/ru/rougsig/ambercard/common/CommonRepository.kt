package ru.rougsig.ambercard.common

import io.realm.Realm
import ru.rougsig.ambercard.common.API.REST.Companion.api
import ru.rougsig.ambercard.utils.enqueue

/**
 * Created by rougs on 17-Oct-17.
 */
object CommonRepository {
    fun getContent(
            onSuccess: () -> Unit
    ) {
        api.getContent().enqueue(
                { _, response ->
                    when (response.code()) {
                        200 -> {
                            val data = response.body()!!
                            Realm.getDefaultInstance().executeTransaction { realm ->
                                realm.insertOrUpdate(data.places)
                                realm.insertOrUpdate(data.categories)
                            }
                            onSuccess()
                        }
                    }
                },
                { _, t -> throw t }
        )
    }
}